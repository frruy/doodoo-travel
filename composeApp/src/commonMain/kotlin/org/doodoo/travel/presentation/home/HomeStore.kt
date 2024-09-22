package org.doodoo.travel.presentation.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch

internal interface HomeStore : Store<HomeStore.Intent, HomeStore.State, HomeStore.Label> {
    sealed interface State {
        val isLoading: Boolean
        val error: String?

        data class Content(
            val homeData: HomeData,
            override val isLoading: Boolean = false,
            override val error: String? = null
        ) : State

        data class Error(
            override val error: String,
            override val isLoading: Boolean = false
        ) : State
    }

    sealed interface Intent {
        data object Refresh : Intent
        data class UpdateHomeData(val newData: String) : Intent
    }

    sealed interface Label {
        data class ErrorOccurred(val error: String) : Label
    }
}

internal class HomeStoreFactory(
    private val storeFactory: StoreFactory,
    private val homeRepository: HomeRepository
) {
    fun create(): HomeStore =
        object : HomeStore, Store<HomeStore.Intent, HomeStore.State, HomeStore.Label> by storeFactory.create(
            name = "HomeStore",
            initialState = HomeStore.State.Content(HomeData(""), isLoading = true),
            bootstrapper = BootstrapperImpl(homeRepository),
            executorFactory = { ExecutorImpl(homeRepository) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object LoadHomeData : Action
        data class HomeDataLoaded(val homeData: HomeData) : Action
        data class HomeDataLoadFailed(val error: String) : Action
    }

    private sealed interface Msg {
        data object Loading : Msg
        data class HomeDataUpdated(val homeData: HomeData) : Msg
        data class ErrorOccurred(val error: String) : Msg
    }

    private class BootstrapperImpl(
        private val homeRepository: HomeRepository
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            dispatch(Action.LoadHomeData)
        }
    }

    private class ExecutorImpl(
        private val homeRepository: HomeRepository
    ) : CoroutineExecutor<HomeStore.Intent, Action, HomeStore.State, Msg, HomeStore.Label>() {
        override fun executeIntent(intent: HomeStore.Intent) {
            when (intent) {
                is HomeStore.Intent.Refresh -> loadHomeData()
                is HomeStore.Intent.UpdateHomeData -> updateHomeData(intent.newData)
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                is Action.LoadHomeData -> loadHomeData()
                is Action.HomeDataLoaded -> dispatch(Msg.HomeDataUpdated(action.homeData))
                is Action.HomeDataLoadFailed -> dispatch(Msg.ErrorOccurred(action.error))
            }
        }

        private fun loadHomeData() {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val homeData = homeRepository.getHomeData()
                    dispatch(Msg.HomeDataUpdated(homeData))
                } catch (e: Exception) {
                    val errorMsg = e.message ?: "Failed to load home data"
                    dispatch(Msg.ErrorOccurred(errorMsg))
                    publish(HomeStore.Label.ErrorOccurred(errorMsg))
                }
            }
        }

        private fun updateHomeData(newData: String) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val updatedData = homeRepository.updateHomeData(newData)
                    dispatch(Msg.HomeDataUpdated(updatedData))
                } catch (e: Exception) {
                    val errorMsg = e.message ?: "Failed to update home data"
                    dispatch(Msg.ErrorOccurred(errorMsg))
                    publish(HomeStore.Label.ErrorOccurred(errorMsg))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<HomeStore.State, Msg> {
        override fun HomeStore.State.reduce(msg: Msg): HomeStore.State =
            when (this) {
                is HomeStore.State.Content -> when (msg) {
                    is Msg.Loading -> copy(isLoading = true, error = null)
                    is Msg.HomeDataUpdated -> copy(homeData = msg.homeData, isLoading = false, error = null)
                    is Msg.ErrorOccurred -> HomeStore.State.Error(msg.error, isLoading = false)
                }

                is HomeStore.State.Error -> when (msg) {
                    is Msg.Loading -> HomeStore.State.Content(HomeData(""), isLoading = true)
                    is Msg.HomeDataUpdated -> HomeStore.State.Content(msg.homeData, isLoading = false)
                    is Msg.ErrorOccurred -> copy(error = msg.error, isLoading = false)
                }
            }
    }
}
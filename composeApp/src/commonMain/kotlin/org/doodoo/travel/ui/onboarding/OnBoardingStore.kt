package org.doodoo.travel.ui.onboarding

import android.util.Log.e
import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.doodoo.travel.core.database.User
import org.doodoo.travel.core.model.TravelGuide
import org.doodoo.travel.data.repository.TravelGuideRepository
import org.doodoo.travel.data.repository.UserDetailRepository
import org.doodoo.travel.ui.home.store.HomeStore
import org.doodoo.travel.ui.main.MainComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface OnBoardingStore : Store<OnBoardingStore.Intent, OnBoardingStore.State, OnBoardingStore.Label> {


    sealed interface State {
        val isLoading: Boolean
        val error: String?

        data class Content(
            val user: User, override val isLoading: Boolean = false, override val error: String? = null,
        ) : State

        data class Error(
            override val isLoading: Boolean = false, override val error: String
        ) : State
    }


    sealed interface Intent {
        data class CreateUser(val user: User) : Intent
    }

    sealed interface Label {
        data class ErrorOccurred(val error: String) : Label
    }
}

internal class OnBoardingStoreFactory(
    private val storeFactory: StoreFactory,
) : KoinComponent {
    private val userDetailRepository by inject<UserDetailRepository>()

    fun create(): OnBoardingStore =
        object : OnBoardingStore,
            Store<OnBoardingStore.Intent, OnBoardingStore.State, OnBoardingStore.Label> by storeFactory.create(
                name = "OnBoarding Store",
                initialState = OnBoardingStore.State.Content(User(0, ""), isLoading = true, error = null),
                bootstrapper = BootstrapperImpl(userDetailRepository),
                executorFactory = { ExecutorImpl(userDetailRepository) },
                reducer = ReducerImpl,
            ) {}

    private sealed interface Action {
        data object CreateUser : Action
        data class UserCreated(val user: User) : Action
        data class UserCreatedError(val error: String) : Action
    }

    private sealed interface Msg {
        data object Loading : Msg
        data class UserCreated(val user: User) : Msg
        data class ErrorOccurred(val error: String) : Msg
    }

    private class BootstrapperImpl(val userDetailRepository: UserDetailRepository) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            dispatch(Action.CreateUser)
        }
    }

    private class ExecutorImpl(
        private val userDetailRepository: UserDetailRepository
    ) : CoroutineExecutor<OnBoardingStore.Intent, Action, OnBoardingStore.State, Msg, OnBoardingStore.Label>() {
        override fun executeIntent(intent: OnBoardingStore.Intent) {
            when (intent) {
                is OnBoardingStore.Intent.CreateUser -> createUser(intent.user)
            }
        }

        private fun createUser(user: User) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val user = userDetailRepository.createUser(user)
                    dispatch(Msg.UserCreated(user))
                } catch (e: Exception) {
                    val errorMsg = e.message ?: "Failed to create user"
                    dispatch(Msg.ErrorOccurred(errorMsg))
                    publish(OnBoardingStore.Label.ErrorOccurred(errorMsg))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<OnBoardingStore.State, Msg> {
        override fun OnBoardingStore.State.reduce(msg: Msg): OnBoardingStore.State = when (this) {
            is OnBoardingStore.State.Content -> when (msg) {
                is Msg.Loading -> copy(isLoading = true, error = null)
                is Msg.UserCreated -> copy(user = msg.user, isLoading = false, error = null)
                is Msg.ErrorOccurred -> OnBoardingStore.State.Error(isLoading = false, error = msg.error)
            }

            is OnBoardingStore.State.Error -> when (msg) {
                is Msg.UserCreated -> OnBoardingStore.State.Content(msg.user, isLoading = false)
                is Msg.Loading -> copy(isLoading = true)
                is Msg.ErrorOccurred -> copy(error = msg.error, isLoading = false)
            }
        }

    }
}
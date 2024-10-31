package org.doodoo.travel.ui.onboarding

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.doodoo.travel.core.database.User
import org.doodoo.travel.data.user.repository.UserDetailRepository
import org.doodoo.travel.ui.base.BaseState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface OnBoardingStore : Store<OnBoardingStore.Intent, OnBoardingStore.State, Nothing> {

    sealed interface State : BaseState {
        data class Content(
            val user: User,
            override val isLoading: Boolean = false,
            override val error: String? = null
        ) : State

        data class Error(
            override val isLoading: Boolean = false,
            override val error: String
        ) : State
    }

    sealed interface Intent {
        data class CreateUser(val user: User) : Intent
    }
}

internal class OnBoardingStoreFactory(
    private val storeFactory: StoreFactory,
) : KoinComponent {
    private val userDetailRepository by inject<UserDetailRepository>()

    fun create(): OnBoardingStore =
        object : OnBoardingStore,
            Store<OnBoardingStore.Intent, OnBoardingStore.State, Nothing> by storeFactory.create(
                name = "OnBoarding Store",
                initialState = OnBoardingStore.State.Content(User(0, "", ""), isLoading = true, error = null),
                executorFactory = { ExecutorImpl(userDetailRepository) },
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class UserCreated(val user: User) : Msg
        data class ErrorOccurred(val error: String) : Msg
    }

    private class ExecutorImpl(
        private val userDetailRepository: UserDetailRepository
    ) : CoroutineExecutor<OnBoardingStore.Intent, Nothing, OnBoardingStore.State, Msg, Nothing>() {
        override fun executeIntent(intent: OnBoardingStore.Intent) {
            when (intent) {
                is OnBoardingStore.Intent.CreateUser -> createUser(intent.user)
            }
        }

        private fun createUser(user: User) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    userDetailRepository.createUser(user)
                    dispatch(Msg.UserCreated(user))
                } catch (e: Exception) {
                    val errorMsg = e.message ?: "Failed to create user"
                    dispatch(Msg.ErrorOccurred(errorMsg))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<OnBoardingStore.State, Msg> {
        override fun OnBoardingStore.State.reduce(msg: Msg): OnBoardingStore.State = when (msg) {
            is Msg.Loading -> {
                OnBoardingStore.State.Content(
                    user = (this as? OnBoardingStore.State.Content)?.user ?: User(0, "", ""),
                    isLoading = true,
                    error = null
                )
            }

            is Msg.UserCreated -> {
                OnBoardingStore.State.Content(
                    user = msg.user,
                    isLoading = false,
                    error = null
                )
            }

            is Msg.ErrorOccurred -> {
                OnBoardingStore.State.Error(
                    isLoading = false,
                    error = msg.error
                )
            }
        }
    }
}
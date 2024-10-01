package org.doodoo.travel.ui.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : ComponentContext by componentContext {

    private val store = ProfileStoreFactory(
        storeFactory = storeFactory
    ).create()

    val state: StateFlow<ProfileStore.State> = store.stateFlow

    fun onNameChanged(name: String) {
        store.accept(ProfileStore.Intent.UpdateName(name))
    }

    fun onEmailChanged(email: String) {
        store.accept(ProfileStore.Intent.UpdateEmail(email))
    }

    fun onSaveProfile() {
        store.accept(ProfileStore.Intent.SaveProfile)
    }
}

interface ProfileStore : Store<ProfileStore.Intent, ProfileStore.State, Nothing> {
    sealed class Intent {
        data class UpdateName(val name: String) : Intent()
        data class UpdateEmail(val email: String) : Intent()
        object SaveProfile : Intent()
    }

    data class State(
        val name: String = "",
        val email: String = "",
        val isSaving: Boolean = false,
        val savedSuccessfully: Boolean = false
    )

    sealed class Result {
        data class NameUpdated(val name: String) : Result()
        data class EmailUpdated(val email: String) : Result()
        object SavingStarted : Result()
        object SavingCompleted : Result()
    }
}

class ProfileStoreFactory(private val storeFactory: StoreFactory) {
    fun create(): ProfileStore =
        object : ProfileStore, Store<ProfileStore.Intent, ProfileStore.State, Nothing> by storeFactory.create(
            name = "ProfileStore",
            initialState = ProfileStore.State(),
            bootstrapper = null,
            executorFactory = ::ProfileExecutor,
            reducer = ProfileReducer
        ) {}
}

class ProfileExecutor : CoroutineExecutor<
        ProfileStore.Intent,
        Nothing,
        ProfileStore.State,
        ProfileStore.Result,
        Nothing
        >() {
    override fun executeIntent(intent: ProfileStore.Intent) {
        when (intent) {
            is ProfileStore.Intent.UpdateName -> {
                dispatch(ProfileStore.Result.NameUpdated(intent.name))
            }
            is ProfileStore.Intent.UpdateEmail -> {
                dispatch(ProfileStore.Result.EmailUpdated(intent.email))
            }
            is ProfileStore.Intent.SaveProfile -> {
                scope.launch {
                    dispatch(ProfileStore.Result.SavingStarted)
                    // Simulate saving delay
                    delay(1000)
                    dispatch(ProfileStore.Result.SavingCompleted)
                }
            }
        }
    }
}

object ProfileReducer : com.arkivanov.mvikotlin.core.store.Reducer<ProfileStore.State, ProfileStore.Result> {
    override fun ProfileStore.State.reduce(result: ProfileStore.Result): ProfileStore.State =
        when (result) {
            is ProfileStore.Result.NameUpdated -> copy(name = result.name)
            is ProfileStore.Result.EmailUpdated -> copy(email = result.email)
            is ProfileStore.Result.SavingStarted -> copy(isSaving = true)
            is ProfileStore.Result.SavingCompleted -> copy(isSaving = false, savedSuccessfully = true)
        }
}
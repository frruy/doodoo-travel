package org.doodoo.travel.ui.root.store

import com.arkivanov.mvikotlin.core.store.Store
import org.doodoo.travel.core.database.User
import org.doodoo.travel.ui.base.BaseState
import org.doodoo.travel.ui.root.store.RootStore.Intent
import org.doodoo.travel.ui.root.store.RootStore.State

interface RootStore : Store<Intent, State, Nothing> {

    sealed interface State : BaseState {
        data class Content(
            val user: User?,
            override val isLoading: Boolean = false,
            override val error: String? = null
        ) : State
    }

    sealed interface Intent

    sealed interface Message {
        data object Loading : Message
        data class UserFetched(val user: User?) : Message
        data class Error(val error: String?) : Message
    }

    sealed interface Action {
        data object FetchUser : Action
    }
}

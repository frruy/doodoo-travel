package org.doodoo.travel.ui.root.store

import com.arkivanov.mvikotlin.core.store.Reducer
import org.doodoo.travel.ui.root.store.RootStore.Message
import org.doodoo.travel.ui.root.store.RootStore.State

object RootReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message): State {
        return when (msg) {
            is Message.Loading -> State.Content(
                user = null,
                isLoading = true,
            )

            is Message.UserFetched -> State.Content(
                user = msg.user,
                isLoading = false,
                error = null
            )

            is Message.Error -> State.Content(
                user = null,
                isLoading = false,
                error = msg.error
            )
        }
    }
}
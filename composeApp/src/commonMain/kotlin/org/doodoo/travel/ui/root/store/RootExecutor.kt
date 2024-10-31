package org.doodoo.travel.ui.root.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.doodoo.travel.core.database.User
import org.doodoo.travel.data.user.repository.UserDetailRepository
import org.doodoo.travel.ui.root.store.RootStore.Intent
import org.doodoo.travel.ui.root.store.RootStore.Action
import org.doodoo.travel.ui.root.store.RootStore.State
import org.doodoo.travel.ui.root.store.RootStore.Message
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RootExecutor : CoroutineExecutor<Intent, Action, State, Message, Nothing>(), KoinComponent {

    private val userDetailRepository: UserDetailRepository by inject()

    override fun executeAction(action: Action) {
        when (action) {
            is Action.FetchUser -> scope.launch {
                try {
                    val users = fetchUserDetails()
                    dispatch(Message.UserFetched(users.firstOrNull()))
                } catch (e: Exception) {
                    val errorMsg = e.message ?: "Failed to fetch users"
                    dispatch(Message.Error(errorMsg))
                }
            }
        }
    }

    private suspend fun fetchUserDetails(): List<User> = userDetailRepository.findAll()
}

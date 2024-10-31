package org.doodoo.travel.ui.root.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.doodoo.travel.core.database.User
import org.doodoo.travel.ui.root.store.RootStore.Intent
import org.doodoo.travel.ui.root.store.RootStore.State

class RootStoreFactory(private val storeFactory: StoreFactory) {

    fun create(): RootStore {
        return RootStoreImpl()
    }

    private inner class RootStoreImpl :
        RootStore,
        Store<Intent, State, Nothing> by storeFactory.create(
            name = "RootStore",
            initialState = State.Content(null, isLoading = true, error = null),
            bootstrapper = RootBootstrapper(),
            executorFactory = { RootExecutor() },
            reducer = RootReducer
        )
}
package org.doodoo.travel.ui.root.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import org.doodoo.travel.ui.root.store.RootStore.Action

class RootBootstrapper : CoroutineBootstrapper<Action>() {
    override fun invoke() {
        dispatch(Action.FetchUser)
    }
}
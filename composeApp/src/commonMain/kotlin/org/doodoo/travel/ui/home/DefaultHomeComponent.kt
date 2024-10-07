package org.doodoo.travel.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.doodoo.travel.ui.home.store.HomeStore
import org.doodoo.travel.ui.home.store.HomeStoreFactory
import org.doodoo.travel.ulti.asValue

internal class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
) : HomeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        HomeStoreFactory(storeFactory).create()
    }

    override val state: Value<HomeState> = store.asValue().map { it.toHomeState() }

    override val labels: Flow<HomeLabel> = store.labels.map { it.toHomeLabel() }

    override fun refresh() {
        store.accept(HomeStore.Intent.Refresh)
    }

    override fun updateHomeData(newData: String) {
        store.accept(HomeStore.Intent.UpdateHomeData(newData))
    }

    private fun HomeStore.State.toHomeState(): HomeState =
        when (this) {
            is HomeStore.State.Content -> HomeState.Content(
                travelGuild = travelGuild,
                isLoading = isLoading,
                error = error
            )

            is HomeStore.State.Error -> HomeState.Error(
                error = error,
                isLoading = isLoading
            )
        }

    private fun HomeStore.Label.toHomeLabel(): HomeLabel =
        when (this) {
            is HomeStore.Label.ErrorOccurred -> HomeLabel.ErrorOccurred(error)
        }
}


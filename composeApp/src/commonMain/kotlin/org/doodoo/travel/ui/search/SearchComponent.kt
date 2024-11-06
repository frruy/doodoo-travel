// SearchComponent.kt
package org.doodoo.travel.ui.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.doodoo.travel.data.place.model.PlaceDetail
import org.doodoo.travel.data.place.repository.PlacesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : ComponentContext by componentContext {

    private val store = SearchStoreFactory(
        storeFactory = storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<SearchStore.State> = store.stateFlow

    fun searchPlace(placeId: String) {
        store.accept(SearchStore.Intent.SearchPlace(placeId))
    }
}

interface SearchStore : Store<SearchStore.Intent, SearchStore.State, Nothing> {
    data class State(
        val placeDetail: PlaceDetail? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class Intent {
        data class SearchPlace(val placeId: String) : Intent()
    }

    sealed class Result {
        data class PlaceLoaded(val place: PlaceDetail) : Result()
        data class Error(val message: String) : Result()
        object Loading : Result()
    }
}

class SearchStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {
    private val placesRepository: PlacesRepository by inject()

    fun create(): SearchStore =
        object : SearchStore, Store<SearchStore.Intent, SearchStore.State, Nothing> by storeFactory.create(
            name = "SearchStore",
            initialState = SearchStore.State(),
            bootstrapper = null,
            executorFactory = { SearchExecutor(placesRepository) },
            reducer = SearchReducer
        ) {}
}

class SearchExecutor(
    private val placesRepository: PlacesRepository
) : CoroutineExecutor<SearchStore.Intent, Nothing, SearchStore.State, SearchStore.Result, Nothing>() {
    override fun executeIntent(intent: SearchStore.Intent) {
        when (intent) {
            is SearchStore.Intent.SearchPlace -> {
                scope.launch {
                    dispatch(SearchStore.Result.Loading)
                    try {
                        val place = placesRepository.getPlaceDetails(intent.placeId)
                        dispatch(SearchStore.Result.PlaceLoaded(place.result))
                    } catch (e: Exception) {
                        dispatch(SearchStore.Result.Error(e.message ?: "Failed to load place details"))
                    }
                }
            }
        }
    }
}

object SearchReducer : com.arkivanov.mvikotlin.core.store.Reducer<SearchStore.State, SearchStore.Result> {
    override fun SearchStore.State.reduce(msg: SearchStore.Result): SearchStore.State =
        when (msg) {
            is SearchStore.Result.Loading -> copy(
                isLoading = true,
                error = null
            )
            is SearchStore.Result.PlaceLoaded -> copy(
                placeDetail = msg.place,
                isLoading = false,
                error = null
            )
            is SearchStore.Result.Error -> copy(
                isLoading = false,
                error = msg.message
            )
        }
}
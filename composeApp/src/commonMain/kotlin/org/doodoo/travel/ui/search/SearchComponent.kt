package org.doodoo.travel.ui.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : ComponentContext by componentContext {

    private val store = SearchStoreFactory(
        storeFactory = storeFactory
    ).create()

    val state: StateFlow<SearchStore.State> = store.stateFlow

    fun onSearchQueryChanged(query: String) {
        store.accept(SearchStore.Intent.UpdateSearchQuery(query))
    }

    fun onSearchSubmit() {
        store.accept(SearchStore.Intent.SubmitSearch)
    }
}

interface SearchStore : Store<SearchStore.Intent, SearchStore.State, Nothing> {
    sealed class Intent {
        data class UpdateSearchQuery(val query: String) : Intent()
        object SubmitSearch : Intent()
    }

    data class State(
        val searchQuery: String = "",
        val searchResults: List<String> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class Result {
        data class SearchQueryUpdated(val query: String) : Result()
        object SearchStarted : Result()
        data class SearchCompleted(val results: List<String>) : Result()
    }
}

class SearchStoreFactory(private val storeFactory: StoreFactory) {
    fun create(): SearchStore =
        object : SearchStore, Store<SearchStore.Intent, SearchStore.State, Nothing> by storeFactory.create(
            name = "SearchStore",
            initialState = SearchStore.State(),
            bootstrapper = null,
            executorFactory = ::SearchExecutor,
            reducer = SearchReducer
        ) {}
}

class SearchExecutor : com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor<
        SearchStore.Intent,
        Nothing,
        SearchStore.State,
        SearchStore.Result,
        Nothing
        >() {
    override fun executeIntent(intent: SearchStore.Intent) {
        when (intent) {
            is SearchStore.Intent.UpdateSearchQuery -> {
                dispatch(SearchStore.Result.SearchQueryUpdated(intent.query))
            }
            is SearchStore.Intent.SubmitSearch -> {
                dispatch(SearchStore.Result.SearchStarted)
                // Simulate search delay
//                delay(1000)
                dispatch(SearchStore.Result.SearchCompleted(listOf("Result 1", "Result 2", "Result 3")))
            }
        }
    }
}

object SearchReducer : com.arkivanov.mvikotlin.core.store.Reducer<SearchStore.State, SearchStore.Result> {
    override fun SearchStore.State.reduce(result: SearchStore.Result): SearchStore.State =
        when (result) {
            is SearchStore.Result.SearchQueryUpdated -> copy(searchQuery = result.query)
            is SearchStore.Result.SearchStarted -> copy(isLoading = true)
            is SearchStore.Result.SearchCompleted -> copy(isLoading = false, searchResults = result.results)
        }
}
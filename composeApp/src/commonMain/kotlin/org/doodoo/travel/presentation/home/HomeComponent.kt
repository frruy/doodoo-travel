package org.doodoo.travel.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow

interface HomeComponent {
    val state: Value<HomeState>
    val labels: Flow<HomeLabel>
    fun refresh()
    fun updateHomeData(newData: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            refresh: () -> Unit,
            updateHomeData: (String) -> Unit,
        ): HomeComponent
    }
}

sealed interface HomeState {
    val isLoading: Boolean
    val error: String?

    data class Content(
        val homeData: HomeData,
        override val isLoading: Boolean,
        override val error: String?
    ) : HomeState

    data class Error(
        override val error: String,
        override val isLoading: Boolean
    ) : HomeState
}

sealed interface HomeLabel {
    data class ErrorOccurred(val error: String) : HomeLabel
}
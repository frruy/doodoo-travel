package org.doodoo.travel.ui.onboarding

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import org.doodoo.travel.core.database.User

interface OnboardingComponent {
    val state: Value<OnboardingState>
    val labels: Flow<OnboardingLabel>
    fun createUser(user: User)
}

sealed interface OnboardingState {
    val isLoading: Boolean
    val error: String?

    data class Content(
        val user: User,
        override val isLoading: Boolean,
        override val error: String?
    ) : OnboardingState

    data class Error(
        override val error: String,
        override val isLoading: Boolean
    ) : OnboardingState
}

sealed interface OnboardingLabel {
    data class ErrorOccurred(val error: String) : OnboardingLabel
}
package org.doodoo.travel.ui.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.doodoo.travel.core.database.User
import org.doodoo.travel.ulti.asValue

class DefaultOnboardingComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory
) : OnboardingComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        OnBoardingStoreFactory(storeFactory).create()
    }

    override val state: Value<OnboardingState> = store.asValue().map { it.toOnBoardingState() }

    override val labels: Flow<OnboardingLabel> = store.labels.map { it.toOnBoardingLabel() }

    override fun createUser(user: User) {
        store.accept(OnBoardingStore.Intent.CreateUser(user))
    }

    private fun OnBoardingStore.State.toOnBoardingState(): OnboardingState =
        when (this) {
            is OnBoardingStore.State.Content -> OnboardingState.Content(
                user = user,
                isLoading = isLoading,
                error = error,
            )

            is OnBoardingStore.State.Error -> OnboardingState.Error(
                error = error,
                isLoading = isLoading,
            )
        }

    private fun OnBoardingStore.Label.toOnBoardingLabel(): OnboardingLabel =
        when (this) {
            is OnBoardingStore.Label.ErrorOccurred -> OnboardingLabel.ErrorOccurred(error)
        }

}
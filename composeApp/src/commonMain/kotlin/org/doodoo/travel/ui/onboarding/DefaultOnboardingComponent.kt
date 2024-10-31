package org.doodoo.travel.ui.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.doodoo.travel.core.database.User
import org.doodoo.travel.ulti.asValue

class DefaultOnboardingComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val onUserCreated: () -> Unit
) : OnboardingComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        OnBoardingStoreFactory(storeFactory).create()
    }

    override val state: Value<OnboardingState> = store.asValue().map { it.toOnBoardingState() }

    override fun createUser(user: User) {
        store.accept(OnBoardingStore.Intent.CreateUser(user))
    }
    override fun onUserCreated() {
        onUserCreated.invoke()
    }

    init {
        // Observe state changes to detect successful user creation
        state.subscribe { currentState ->
            when (currentState) {
                is OnboardingState.Content -> {
                    if (!currentState.isLoading && currentState.user.id.toInt() != 0) {
                        onUserCreated() // Trigger navigation callback
                    }
                }
                else -> {} // Handle other states if needed
            }
        }
    }

    private fun OnBoardingStore.State. toOnBoardingState(): OnboardingState =
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

}
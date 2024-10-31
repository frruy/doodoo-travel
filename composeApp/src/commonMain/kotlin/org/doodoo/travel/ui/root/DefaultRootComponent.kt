package org.doodoo.travel.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.doodoo.travel.ui.main.DefaultMainComponent
import org.doodoo.travel.ui.onboarding.DefaultOnboardingComponent
import org.doodoo.travel.ui.root.store.RootStore
import org.doodoo.travel.ui.root.store.RootStoreFactory
import org.doodoo.travel.ui.splash.SplashComponent
import org.doodoo.travel.ulti.asValue

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootComponent.Config>()

    private val json = Json { serializersModule = RootComponent.Config.serializer }

    override val childStack: Value<ChildStack<RootComponent.Config, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = json.serializersModule.serializer(),
            initialConfiguration = RootComponent.Config.Splash,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private val store = instanceKeeper.getStore {
        RootStoreFactory(storeFactory).create()
    }

    override val state: Value<RootState> = store.asValue().map { it.toRootState() }

    init {
        // Observe state changes and navigate when user is fetched
        state.subscribe { currentState ->
            when (currentState) {
                is RootState.Content -> handleContentState(currentState)
            }
        }
    }

    private fun handleContentState(state: RootState.Content) {
        // Only proceed if loading is complete
        if (state.isLoading) {
            println("Loading in progress, skipping navigation.")
            return
        }

        // Determine the next screen based on user presence
        val nextConfig = if (state.user != null) {
            println("User found, navigating to Main.")
            RootComponent.Config.Main
        } else {
            println("No user found, navigating to Onboarding.")
            RootComponent.Config.Onboarding
        }

        val currentConfig = childStack.value.active.configuration

        // Push the new configuration if it's different from the current one
        if (currentConfig != nextConfig) {
            navigation.push(nextConfig)
        }
    }

    private fun createChild(
        config: RootComponent.Config,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is RootComponent.Config.Splash -> RootComponent.Child.Splash(
                SplashComponent(
                    componentContext,
                    storeFactory
                )
            )

            is RootComponent.Config.Onboarding -> RootComponent.Child.Onboarding(
                DefaultOnboardingComponent(
                    componentContext,
                    storeFactory,
                    onUserCreated = {
                        // Navigate to Main when user is created
                        navigation.push(RootComponent.Config.Main)
                    }
                )
            )

            is RootComponent.Config.Main -> RootComponent.Child.Main(
                DefaultMainComponent(
                    componentContext,
                    storeFactory,
                )
            )
        }

    override fun onSplashFinished() {
        when (val currentState = state.value) {
            is RootState.Content -> {
                if (currentState.user != null) {
                    navigation.push(RootComponent.Config.Main)
                } else {
                    navigation.push(RootComponent.Config.Onboarding)
                }
            }
        }
    }

    private fun RootStore.State.toRootState(): RootState =
        when (this) {
            is RootStore.State.Content -> RootState.Content(
                user = user,
                isLoading = isLoading,
                error = error,
            )
        }
}
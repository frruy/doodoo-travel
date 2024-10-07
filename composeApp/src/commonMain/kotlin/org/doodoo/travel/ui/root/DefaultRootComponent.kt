package org.doodoo.travel.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.doodoo.travel.ui.main.DefaultMainComponent
import org.doodoo.travel.ui.onboarding.DefaultOnboardingComponent
import org.doodoo.travel.ui.splash.SplashComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootComponent.Config>()

    private val json = Json { serializersModule = RootComponent.Config.serializer }

    override val childStack: Value<ChildStack<RootComponent.Config, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = json.serializersModule.serializer(),
        initialConfiguration = RootComponent.Config.Splash,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: RootComponent.Config, componentContext: ComponentContext): RootComponent.Child =
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
        navigation.push(RootComponent.Config.Onboarding)
    }
}
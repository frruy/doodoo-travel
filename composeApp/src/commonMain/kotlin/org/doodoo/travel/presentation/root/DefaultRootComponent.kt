package org.doodoo.travel.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import org.doodoo.travel.presentation.home.HomeComponent
import org.doodoo.travel.presentation.root.RootComponent.Config

internal class DefaultRootComponent(
    componentContext: ComponentContext,
    private val homeComponentFactory: HomeComponent.Factory,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<Config, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Home -> RootComponent.Child.Home(
                homeComponentFactory(
                    componentContext = componentContext,
                    refresh = { navigation.bringToFront(Config.Home) },
                    updateHomeData = { newData -> "updateHomeData: home" }
                )
            )

            is Config.Search -> RootComponent.Child.Search(homeComponentFactory(
                componentContext = componentContext,
                refresh = { navigation.bringToFront(Config.Home) },
                updateHomeData = { newData -> "updateHomeData: search" }
            ))

            is Config.Profile -> RootComponent.Child.Profile(homeComponentFactory(
                componentContext = componentContext,
                refresh = { navigation.bringToFront(Config.Home) },
                updateHomeData = { newData -> "updateHomeData: profile" }
            ))
        }

    override fun onHomeTabClicked() {
        navigation.bringToFront(Config.Home)
    }

    override fun onSearchTabClicked() {
        navigation.bringToFront(Config.Search)
    }

    override fun onProfileTabClicked() {
        navigation.bringToFront(Config.Profile)
    }

    class Factory(
        private val homeComponentFactory: HomeComponent.Factory,
    ) : RootComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
        ): RootComponent {
            return DefaultRootComponent(
                componentContext = componentContext,
                homeComponentFactory = homeComponentFactory,
            )
        }
    }
}
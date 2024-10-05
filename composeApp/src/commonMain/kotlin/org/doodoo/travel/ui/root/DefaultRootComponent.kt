package org.doodoo.travel.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.doodoo.travel.ui.home.DefaultHomeComponent
import org.doodoo.travel.ui.root.RootComponent.Config

internal class DefaultRootComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
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
//            is Config.Home -> RootComponent.Child.Home(
//                homeComponentFactory(
//                    componentContext = componentContext,
//                    refresh = { navigation.bringToFront(Config.Home) },
//                    updateHomeData = { newData -> "updateHomeData: home" }
//                )
//            )

            is Config.Home -> RootComponent.Child.Home(
                DefaultHomeComponent(
                    componentContext = componentContext,
                    storeFactory = storeFactory
                )
            )

            is Config.Search -> RootComponent.Child.Search(
                DefaultHomeComponent(
                    componentContext = componentContext,
                    storeFactory = storeFactory
                )
            )

            is Config.Profile -> RootComponent.Child.Profile(
                DefaultHomeComponent(
                    componentContext = componentContext,
                    storeFactory = storeFactory
                )
            )
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
}
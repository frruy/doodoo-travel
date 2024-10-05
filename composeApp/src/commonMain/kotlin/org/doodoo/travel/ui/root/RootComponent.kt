package org.doodoo.travel.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.doodoo.travel.ui.home.HomeComponent

interface RootComponent {
    val childStack: Value<ChildStack<Config, Child>>

    fun onHomeTabClicked()
    fun onSearchTabClicked()
    fun onProfileTabClicked()

    sealed class Child {
        data class Home(val component: HomeComponent) : Child()
        data class Search(val component: HomeComponent) : Child()
        data class Profile(val component: HomeComponent) : Child()
    }

    @Serializable
    sealed class Config {
        @Serializable
        data object Home : Config()

        @Serializable
        data object Search : Config()

        @Serializable
        data object Profile : Config()
    }
}
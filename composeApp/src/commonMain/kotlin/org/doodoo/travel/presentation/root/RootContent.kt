package org.doodoo.travel.presentation.root

import HomeContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun RootContent(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = childStack.active.configuration is RootComponent.Config.Home,
                    onClick = { component.onHomeTabClicked() }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = childStack.active.configuration is RootComponent.Config.Search,
                    onClick = { component.onSearchTabClicked() }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = childStack.active.configuration is RootComponent.Config.Profile,
                    onClick = { component.onProfileTabClicked() }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Children(stack = childStack) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.Home -> HomeContent(instance.component)
                    is RootComponent.Child.Search -> HomeContent(instance.component)
                    is RootComponent.Child.Profile -> HomeContent(instance.component)
                }
            }
        }
    }
}
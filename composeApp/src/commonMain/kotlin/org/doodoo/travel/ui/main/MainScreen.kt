package org.doodoo.travel.ui.main

import HomeScreen
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
fun MainScreen(component: MainComponent) {
    val childStack by component.childStack.subscribeAsState()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = childStack.active.configuration is MainComponent.Config.Home,
                    onClick = { component.onHomeTabClicked() }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = childStack.active.configuration is MainComponent.Config.Search,
                    onClick = { component.onSearchTabClicked() }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = childStack.active.configuration is MainComponent.Config.Profile,
                    onClick = { component.onProfileTabClicked() }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Children(stack = childStack) { child ->
                when (val instance = child.instance) {
                    is MainComponent.Child.Home -> HomeScreen(instance.component)
                    is MainComponent.Child.Search -> HomeScreen(instance.component)
                    is MainComponent.Child.Profile -> HomeScreen(instance.component)
                }
            }
        }
    }
}
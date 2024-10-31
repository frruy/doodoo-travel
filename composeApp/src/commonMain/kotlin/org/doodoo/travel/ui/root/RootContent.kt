package org.doodoo.travel.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import org.doodoo.travel.ui.main.MainScreen
import org.doodoo.travel.ui.onboarding.OnboardingScreen
import org.doodoo.travel.ui.splash.SplashScreen

@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Splash -> SplashScreen()
            is RootComponent.Child.Onboarding -> OnboardingScreen(instance.component)
            is RootComponent.Child.Main -> MainScreen(instance.component)
        }
    }
}


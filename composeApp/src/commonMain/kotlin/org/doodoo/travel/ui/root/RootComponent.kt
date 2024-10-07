package org.doodoo.travel.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.doodoo.travel.ui.main.MainComponent
import org.doodoo.travel.ui.onboarding.OnboardingComponent
import org.doodoo.travel.ui.splash.SplashComponent

interface RootComponent {
    val childStack: Value<ChildStack<Config, Child>>

    fun onSplashFinished()

    @Serializable
    sealed class Config {
        @Serializable
        object Splash : Config()
        @Serializable
        object Onboarding : Config()
        @Serializable
        object Main : Config()

        companion object {
            val serializer = SerializersModule {
                polymorphic(Config::class) {
                    subclass(Splash::class)
                    subclass(Onboarding::class)
                    subclass(Main::class)
                }
            }
        }
    }

    sealed class Child {
        class Splash(val component: SplashComponent) : Child()
        class Onboarding(val component: OnboardingComponent) : Child()
        class Main(val component: MainComponent) : Child()
    }
}
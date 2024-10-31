package org.doodoo.travel.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.doodoo.travel.core.database.User
import org.doodoo.travel.ui.main.MainComponent
import org.doodoo.travel.ui.onboarding.OnboardingComponent
import org.doodoo.travel.ui.splash.SplashComponent

interface RootComponent {
    val childStack: Value<ChildStack<Config, Child>>
    val state: Value<RootState>

    fun onSplashFinished()

    @Serializable
    sealed class Config {
        @Serializable
        data object Splash : Config()

        @Serializable
        data object Onboarding : Config()

        @Serializable
        data object Main : Config()

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

sealed interface RootState {
    val isLoading: Boolean
    val error: String?

    data class Content(
        val user: User? = null,
        override val isLoading: Boolean,
        override val error: String?
    ) : RootState
}

package org.doodoo.travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import org.doodoo.travel.ui.root.RootComponent
import org.kodein.di.instance

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponentFactory: RootComponent.Factory by kodein.instance()
        val rootComponent = rootComponentFactory(defaultComponentContext())

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}

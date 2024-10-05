package org.doodoo.travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.doodoo.travel.core.di.initKoin
import org.doodoo.travel.ui.root.DefaultRootComponent
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin(enableNetworkLogs = true) {
            androidContext(applicationContext)
        }

        val rootComponent = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            storeFactory = DefaultStoreFactory(),
        )

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}
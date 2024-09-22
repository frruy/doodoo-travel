package org.doodoo.travel

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import org.doodoo.travel.presentation.home.*
import org.doodoo.travel.presentation.home.DefaultHomeComponent
import org.doodoo.travel.presentation.home.HomeStoreFactory
import org.doodoo.travel.presentation.root.DefaultRootComponent
import org.doodoo.travel.presentation.root.RootComponent
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val kodein = DI {
    // repository
    bindSingleton<HomeRepository> { DefaultHomeRepository() }

    // store
    bindSingleton<StoreFactory> { LoggingStoreFactory(TimeTravelStoreFactory())}

    // home
    bindSingleton<HomeComponent.Factory> {
        DefaultHomeComponent.Factory(instance())
    }

    bindSingleton {
        HomeStoreFactory(
            storeFactory = instance(),
            homeRepository = instance()
        )
    }

    // root
    bindSingleton< RootComponent.Factory> {
        DefaultRootComponent.Factory(
            homeComponentFactory = instance()
        )
    }
}
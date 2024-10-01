package org.doodoo.travel

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.doodoo.travel.data.repository.TravelGuideRepository
import org.doodoo.travel.data.repository.TravelGuideRepositoryImpl
import org.doodoo.travel.ui.home.DefaultHomeComponent
import org.doodoo.travel.ui.home.HomeComponent
import org.doodoo.travel.ui.home.HomeStoreFactory
import org.doodoo.travel.ui.root.DefaultRootComponent
import org.doodoo.travel.ui.root.RootComponent
import org.kodein.di.*

val kodein = DI {

    bind<HttpClient>() with singleton { HttpClient(CIO) }

    // repository
    bindSingleton<TravelGuideRepository> { TravelGuideRepositoryImpl(instance()) }

    // store
    bindSingleton<StoreFactory> { LoggingStoreFactory(TimeTravelStoreFactory())}

    // home
    bindSingleton<HomeComponent.Factory> {
        DefaultHomeComponent.Factory(instance())
    }

    bindSingleton {
        HomeStoreFactory(
            storeFactory = instance(),
            travelGuideRepository = instance()
        )
    }

    // root
    bindSingleton< RootComponent.Factory> {
        DefaultRootComponent.Factory(
            homeComponentFactory = instance()
        )
    }
}
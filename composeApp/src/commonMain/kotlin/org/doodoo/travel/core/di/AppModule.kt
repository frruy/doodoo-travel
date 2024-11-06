package org.doodoo.travel.core.di

import org.doodoo.travel.core.database.di.databaseModule
import org.doodoo.travel.core.network.networkModule
import org.doodoo.travel.data.dataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            databaseModule,
            dataModule,
            networkModule
        )
    }
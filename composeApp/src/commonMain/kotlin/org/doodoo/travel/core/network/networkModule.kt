package org.doodoo.travel.core.network

import org.doodoo.travel.data.travelguide.service.TravelGuideService
import org.koin.dsl.module

val networkModule =
    module {
        single { createHttpClient() }
        single { TravelGuideService(httpClient = get()) }
    }
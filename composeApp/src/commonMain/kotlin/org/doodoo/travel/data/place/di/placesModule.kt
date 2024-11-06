// Path: composeApp/src/commonMain/kotlin/org/doodoo/travel/data/place/di/PlacesModule.kt

package org.doodoo.travel.data.place.di

import org.doodoo.travel.data.place.repository.PlacesRepository
import org.doodoo.travel.data.place.repository.PlacesRepositoryImpl
import org.doodoo.travel.data.place.service.PlacesService
import org.koin.dsl.module

val placesModule = module {
    single { 
        PlacesService(
            httpClient = get(),
            apiKey = "AIzaSyBj-lhgxch1Om1NFhQ9uCjPav_bUAVkyi8"
        )
    }
    single<PlacesRepository> { PlacesRepositoryImpl(placesService = get()) }
}
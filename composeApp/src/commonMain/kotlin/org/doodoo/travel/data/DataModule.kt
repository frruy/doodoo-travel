package org.doodoo.travel.data

import org.doodoo.travel.data.travelguide.repository.TravelGuideRepository
import org.doodoo.travel.data.travelguide.repository.TravelGuideRepositoryImpl
import org.doodoo.travel.data.user.repository.UserDetailRepository
import org.doodoo.travel.data.user.repository.UserDetailRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<TravelGuideRepository> { TravelGuideRepositoryImpl() }
    single<UserDetailRepository> { UserDetailRepositoryImpl() }
}
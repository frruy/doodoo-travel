package org.doodoo.travel.data

import org.doodoo.travel.data.repository.TravelGuideRepository
import org.doodoo.travel.data.repository.TravelGuideRepositoryImpl
import org.doodoo.travel.data.repository.UserDetailRepository
import org.doodoo.travel.data.repository.UserDetailRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<TravelGuideRepository> { TravelGuideRepositoryImpl() }
    single<UserDetailRepository> { UserDetailRepositoryImpl() }
}
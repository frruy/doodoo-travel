package org.doodoo.travel.core.database.di

import org.doodoo.travel.core.database.createDatabase
import org.doodoo.travel.core.database.dao.UserDao
import org.doodoo.travel.core.database.sqlDriverFactory
import org.koin.dsl.module

val databaseModule = module {
    factory { sqlDriverFactory() }
    single { createDatabase(driver = get()) }
    single { UserDao(travelPlanningDatabase = get()) }
}
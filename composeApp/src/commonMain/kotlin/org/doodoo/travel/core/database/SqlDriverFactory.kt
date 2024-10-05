package org.doodoo.travel.core.database

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope

expect fun Scope.sqlDriverFactory(): SqlDriver
fun createDatabase(driver: SqlDriver): TravelPlanningDatbase {
    val database = TravelPlanningDatbase(
        driver = driver,
    )

    return database
}
package org.doodoo.travel.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mocoding.pokedex.core.database.DatabaseConstants
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return AndroidSqliteDriver(TravelPlanningDatbase.Schema, androidContext(), "${DatabaseConstants.name}.db")
}
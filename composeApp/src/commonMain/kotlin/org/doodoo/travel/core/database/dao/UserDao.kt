package org.doodoo.travel.core.database.dao

import com.mocoding.pokedex.travelPlanningDispatchers
import kotlinx.coroutines.withContext
import org.doodoo.travel.core.database.TravelPlanningDatbase
import org.doodoo.travel.core.database.User

class UserDao(
    private val travelPlanningDatabase: TravelPlanningDatbase
) {
    private val query get() = travelPlanningDatabase.userQueries

    suspend fun insert(user: User) = withContext(travelPlanningDispatchers.io) {
        query.insertUser(user.id, user.name)
    }
}
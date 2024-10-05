package org.doodoo.travel.data.repository

import org.doodoo.travel.core.database.User


interface UserDetailRepository {
    suspend fun createUser(user : User)
}
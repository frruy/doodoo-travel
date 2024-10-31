package org.doodoo.travel.data.user.repository

import org.doodoo.travel.core.database.User


interface UserDetailRepository {
    suspend fun createUser(user : User) : User
    suspend fun findAll() : List<User>
}
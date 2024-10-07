package org.doodoo.travel.data.repository

import org.doodoo.travel.core.database.User
import org.doodoo.travel.core.database.dao.UserDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserDetailRepositoryImpl: UserDetailRepository, KoinComponent {

    private val userDao : UserDao by inject<UserDao>()

    override suspend fun createUser(user: User): User {
        userDao.insert(user)
        return user
    }
}
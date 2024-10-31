package org.doodoo.travel.data.user.repository

import kotlinx.coroutines.delay
import org.doodoo.travel.core.database.User
import org.doodoo.travel.core.database.dao.UserDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserDetailRepositoryImpl : UserDetailRepository, KoinComponent {

    private val userDao: UserDao by inject<UserDao>()

    override suspend fun createUser(user: User): User {
        userDao.insert(user)
        return user
    }

    override suspend fun findAll() : List<User> {
        return userDao.findAll()
    }
}
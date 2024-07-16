package com.abzer.weatherapp.data.repository.user

import com.abzer.weatherapp.data.dao.UserDao
import com.abzer.weatherapp.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserRepository {

    override suspend fun addOrUpdateUser(user: User) = userDao.addOrUpdateUser(user)

    override suspend fun isUserExist(email: String) = userDao.isUserExist(email)

    override suspend fun findUserByPassword(email: String, password: String) =
        userDao.findUserByPassword(email, password)

    override suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    override fun getAllUsers() = userDao.getAllUsers()

}
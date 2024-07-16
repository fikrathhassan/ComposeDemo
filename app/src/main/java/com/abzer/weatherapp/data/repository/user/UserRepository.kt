package com.abzer.weatherapp.data.repository.user

import com.abzer.weatherapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun addOrUpdateUser(user: User): Long

    suspend fun isUserExist(email: String): User?

    suspend fun findUserByPassword(email: String, password: String): User?

    suspend fun deleteUser(user: User): Int

    fun getAllUsers(): Flow<List<User>>

}
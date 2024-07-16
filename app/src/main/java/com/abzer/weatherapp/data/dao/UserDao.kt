package com.abzer.weatherapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.abzer.weatherapp.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun addOrUpdateUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun isUserExist(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    fun findUserByPassword(email: String, password: String): User?

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

}
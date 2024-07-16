package com.abzer.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abzer.weatherapp.data.dao.UserDao
import com.abzer.weatherapp.data.model.User

@Database(
    entities = [(User::class)],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
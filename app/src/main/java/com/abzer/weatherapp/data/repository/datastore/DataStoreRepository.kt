package com.abzer.weatherapp.data.repository.datastore

import com.abzer.weatherapp.util.Users
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    val isStaySignedIn: Flow<Boolean>

    suspend fun setStaySignedIn(isStaySignedIn: Boolean)

    val loggedUser: Flow<String>

    suspend fun setLoggedUser(user: Users)

}
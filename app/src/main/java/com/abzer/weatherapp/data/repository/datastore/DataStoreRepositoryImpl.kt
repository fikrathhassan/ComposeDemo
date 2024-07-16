package com.abzer.weatherapp.data.repository.datastore

import com.abzer.weatherapp.util.DatastoreManager
import com.abzer.weatherapp.util.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    private val datastoreManager: DatastoreManager
): DataStoreRepository {

    override val isStaySignedIn: Flow<Boolean> = datastoreManager.isStaySignedIn

    override suspend fun setStaySignedIn(isStaySignedIn: Boolean) {
        datastoreManager.setStaySignedIn(isStaySignedIn)
    }

    override val loggedUser: Flow<String> = datastoreManager.loggedUser

    override suspend fun setLoggedUser(user: Users) {
        datastoreManager.setLoggedUser(user)
    }

}
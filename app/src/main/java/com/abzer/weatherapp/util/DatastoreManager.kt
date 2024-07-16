package com.abzer.weatherapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abzer.weatherapp.util.DatastoreManager.PreferencesKeys.KEY_LOGGED_USER
import com.abzer.weatherapp.util.DatastoreManager.PreferencesKeys.KEY_STAY_SIGNED_IN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreManager @Inject constructor(
    private val context: Context
) {

    companion object {
        private const val PREFERENCES_NAME = "data"

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
    }

    private object PreferencesKeys {
        val KEY_LOGGED_USER = stringPreferencesKey("LOGGED_USER")
        val KEY_STAY_SIGNED_IN = booleanPreferencesKey("STAY_SIGNED_IN")
    }

    val loggedUser: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_LOGGED_USER] ?: Users.USER.name
    }

    suspend fun setLoggedUser(loggedUser: Users) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LOGGED_USER] = loggedUser.name
        }
    }

    val isStaySignedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_STAY_SIGNED_IN] ?: false
    }

    suspend fun setStaySignedIn(isStaySignedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_STAY_SIGNED_IN] = isStaySignedIn
        }
    }

}
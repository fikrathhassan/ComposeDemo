package com.abzer.weatherapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abzer.weatherapp.util.DatastoreManager.PreferencesKeys.KEY_IS_LOGGED_IN
import com.abzer.weatherapp.util.DatastoreManager.PreferencesKeys.KEY_LOGGED_USER
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    companion object {
        private const val PREFERENCES_NAME = "data"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
    }

    private object PreferencesKeys {
        val KEY_IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
        val KEY_LOGGED_USER = stringPreferencesKey("LOGGED_USER")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_IS_LOGGED_IN] ?: false
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_IS_LOGGED_IN] = isLoggedIn
        }
    }

    val loggedUser: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_LOGGED_USER] ?: Users.NONE.name
    }

    suspend fun setLoggedUser(loggedUser: Users) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LOGGED_USER] = loggedUser.name
        }
    }

}
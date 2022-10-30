package com.daviper.sponsorvisa.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first

class AppPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun updateDatabaseVersion(newVersion: Long) {
        dataStore.edit { store ->
            store[PreferencesKeys.DATABASE_VERSION] = newVersion
        }
    }

    suspend fun getDatabaseVersion(): Long? {
        return dataStore.data.first()[PreferencesKeys.DATABASE_VERSION]
    }

    companion object {
        const val TAG: String = "AppPreferencesRepo"

        object PreferencesKeys {
            val DATABASE_VERSION = longPreferencesKey("VERSION_NO")
        }
    }
}
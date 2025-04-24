package com.narvatov.happybirthday.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class BirthdayDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    suspend fun getName(): String {
        return context.dataStore.data.map { preferences ->
            preferences[NAME_KEY] ?: ""
        }.firstOrNull() ?: ""
    }

    suspend fun updateName(name: String) {
        context.dataStore.edit { settings ->
            settings[NAME_KEY] = name
        }
    }

    suspend fun getBirthday(): Long? {
        return context.dataStore.data.map { preferences ->
            preferences[BIRTHDAY_KEY]
        }.firstOrNull()
    }

    suspend fun updateBirthday(birthday: Long) {
        context.dataStore.edit { settings ->
            settings[BIRTHDAY_KEY] = birthday
        }
    }

    suspend fun getPicture(): String {
        return context.dataStore.data.map { preferences ->
            preferences[PICTURE_KEY] ?: ""
        }.firstOrNull() ?: ""
    }

    suspend fun updatePicture(picture: String) {
        context.dataStore.edit { settings ->
            settings[PICTURE_KEY] = picture
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "birthdayInfo"
        private val NAME_KEY = stringPreferencesKey("name")
        private val BIRTHDAY_KEY = longPreferencesKey("birthday")
        private val PICTURE_KEY = stringPreferencesKey("picture")
    }
}
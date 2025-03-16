package com.samzuhalsetiawan.floss.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private typealias AppPreferences = com.samzuhalsetiawan.floss.domain.preferences.Preferences

private val Context.preferences: DataStore<Preferences> by preferencesDataStore("app_preferences")

class PreferencesImpl(
   private val applicationContext: Context
): AppPreferences {

   companion object {
      private val IS_FIRST_LAUNCH_KEY = booleanPreferencesKey("is_first_launch")
   }

   override val isFirstLaunch: Flow<Boolean>
      get() = applicationContext.preferences.data.map { preferences ->
         preferences[IS_FIRST_LAUNCH_KEY] != false
      }

   override suspend fun setIsFirstLaunch(isFirstLaunch: Boolean) {
      applicationContext.preferences.edit { preferences ->
         preferences[IS_FIRST_LAUNCH_KEY] = isFirstLaunch
      }
   }
}
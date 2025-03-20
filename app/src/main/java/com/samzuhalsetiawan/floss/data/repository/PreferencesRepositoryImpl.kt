package com.samzuhalsetiawan.floss.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.samzuhalsetiawan.floss.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first

private val Context.preferences: DataStore<Preferences> by preferencesDataStore("app_preferences")

class PreferencesRepositoryImpl(
   private val applicationContext: Context
): PreferencesRepository {

   companion object {
      private val IS_FIRST_LAUNCH_KEY = booleanPreferencesKey("is_first_launch")
   }

   override suspend fun isFirstLaunch(): Boolean {
      return applicationContext.preferences.data.first()[IS_FIRST_LAUNCH_KEY] != false
   }

   override suspend fun setIsFirstLaunch(isFirstLaunch: Boolean) {
      applicationContext.preferences.edit { preferences ->
         preferences[IS_FIRST_LAUNCH_KEY] = isFirstLaunch
      }
   }
}
package com.samzuhalsetiawan.floss.domain.repository

interface PreferencesRepository {
   suspend fun isFirstLaunch(): Boolean
   suspend fun setIsFirstLaunch(isFirstLaunch: Boolean)
}
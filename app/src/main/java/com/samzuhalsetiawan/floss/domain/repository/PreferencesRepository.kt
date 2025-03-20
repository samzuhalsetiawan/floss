package com.samzuhalsetiawan.floss.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
   suspend fun isFirstLaunch(): Boolean
   suspend fun setIsFirstLaunch(isFirstLaunch: Boolean)
}
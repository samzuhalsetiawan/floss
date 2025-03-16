package com.samzuhalsetiawan.floss.domain.preferences

import kotlinx.coroutines.flow.Flow

interface Preferences {
   val isFirstLaunch: Flow<Boolean>
   suspend fun setIsFirstLaunch(isFirstLaunch: Boolean)
}
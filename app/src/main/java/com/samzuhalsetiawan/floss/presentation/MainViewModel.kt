package com.samzuhalsetiawan.floss.presentation

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.preferences.Preferences
import com.samzuhalsetiawan.floss.presentation.common.config.Config
import com.samzuhalsetiawan.floss.presentation.navigation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class MainViewModel(
   private val preferences: Preferences
): ViewModel() {

   private val _isAppReady = MutableStateFlow(false)
   val isAppReady = _isAppReady.asStateFlow()

   private val _startDestination = MutableStateFlow<Destination.Graph?>(null)
   val startDestination = _startDestination.asStateFlow()

   init {
      viewModelScope.launch {
         val isFirstLaunch = preferences.isFirstLaunch.first()
         _startDestination.update {
            if (isFirstLaunch) {
               Destination.Graph.Welcome
            } else {
               Destination.Graph.Main
            }
         }
         _isAppReady.update { true }
      }
   }
}
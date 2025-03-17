package com.samzuhalsetiawan.floss.presentation

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.session.MediaController
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

   private val _state = MutableStateFlow(MainActivityState())
   val state = _state.asStateFlow()

   fun onEvent(event: MainActivityEvent) {
      when (event) {
         is MainActivityEvent.SetMediaController -> onSetMediaController(event.mediaController)
      }
   }

   init {
      viewModelScope.launch {
         val isFirstLaunch = preferences.isFirstLaunch.first()
         val startDestination = if (isFirstLaunch) Destination.Graph.Welcome else Destination.Graph.Main
         _state.update { currentState ->
            currentState.copy(
               isAppReady = true,
               startDestination = startDestination
            )
         }
      }
   }

   private fun onSetMediaController(mediaController: MediaController) {
      _state.update { currentState ->
         currentState.copy(mediaController = mediaController)
      }
   }

}
package com.samzuhalsetiawan.floss.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.usecase.GetIsFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.ReleasePlayerResources
import com.samzuhalsetiawan.floss.presentation.navigation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
   private val getIsFirstLaunch: GetIsFirstLaunch,
   private val releasePlayerResources: ReleasePlayerResources
): ViewModel() {

   private val _state = MutableStateFlow(MainActivityState())
   val state = _state.asStateFlow()

   fun onEvent(event: MainActivityEvent) {
      when (event) {
         MainActivityEvent.OnActivityDestroyed -> onActivityDestroyed()
      }
   }

   private fun onActivityDestroyed() {
      releasePlayerResources()
   }

   init {
      viewModelScope.launch {
         val isFirstLaunch = getIsFirstLaunch()
         val startDestination = if (isFirstLaunch) Destination.Graph.Welcome else Destination.Graph.Main
         _state.update { currentState ->
            currentState.copy(
               isAppReady = true,
               startDestination = startDestination
            )
         }
      }
   }
}
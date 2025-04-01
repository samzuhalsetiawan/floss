package com.samzuhalsetiawan.floss.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.usecase.GetIsFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.ReleasePlayerResources
import com.samzuhalsetiawan.floss.presentation.navigation.destination.Destination
import kotlinx.coroutines.launch

class MainActivityViewModel(
   private val getIsFirstLaunch: GetIsFirstLaunch,
   private val releasePlayerResources: ReleasePlayerResources
): ViewModel() {

   fun releaseResources() {
      releasePlayerResources()
   }

   fun waitUntilInitializationCompleted(
      onInitializationCompleted: (startDestination: Destination.Graph) -> Unit
   ) {
      viewModelScope.launch {
         val isFirstLaunch = getIsFirstLaunch()
         val startDestination = if (isFirstLaunch) Destination.Graph.Welcome else Destination.Graph.Main
         onInitializationCompleted(startDestination)
      }
   }
}
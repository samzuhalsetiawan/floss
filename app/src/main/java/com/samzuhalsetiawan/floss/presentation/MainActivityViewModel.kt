package com.samzuhalsetiawan.floss.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.usecase.DecideStartDestination
import com.samzuhalsetiawan.floss.domain.usecase.ReleasePlayerResources
import com.samzuhalsetiawan.floss.domain.Destination
import kotlinx.coroutines.launch

class MainActivityViewModel(
   private val decideStartDestination: DecideStartDestination,
   private val releasePlayerResources: ReleasePlayerResources
): ViewModel() {

   fun releaseResources() {
      releasePlayerResources()
   }

   fun waitUntilInitializationCompleted(
      onInitializationCompleted: (startDestination: Destination.Graph) -> Unit
   ) {
      viewModelScope.launch {
         val startDestination = decideStartDestination()
         onInitializationCompleted(startDestination)
      }
   }
}
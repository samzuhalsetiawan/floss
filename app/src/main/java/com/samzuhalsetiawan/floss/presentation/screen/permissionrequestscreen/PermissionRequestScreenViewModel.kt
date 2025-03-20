package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.usecase.SetIsFirstLaunch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PermissionRequestScreenViewModel(
   private val setIsFirstLaunch: SetIsFirstLaunch
): ViewModel() {

   private val _state = MutableStateFlow(PermissionRequestScreenState())
   val state = _state.asStateFlow()

   private val _navigationEvent = Channel<PermissionRequestScreenNavigationEvent>()
   val navigationEvent = _navigationEvent.receiveAsFlow()

   fun onEvent(event: PermissionRequestScreenEvent) {
      when (event) {
         is PermissionRequestScreenEvent.OnPermissionLauncherResult -> onPermissionLauncherResult()
      }
   }

   private fun onPermissionLauncherResult() {
      viewModelScope.launch {
         _navigationEvent.send(PermissionRequestScreenNavigationEvent.NavigateToMusicListScreen)
         setIsFirstLaunch(false)
      }
   }

}
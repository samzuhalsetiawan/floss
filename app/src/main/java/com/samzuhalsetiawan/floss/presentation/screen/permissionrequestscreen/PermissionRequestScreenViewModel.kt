package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.manager.PermissionResult
import com.samzuhalsetiawan.floss.domain.usecase.MarkAsNotFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.RequestReadAudioFilesPermission
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PermissionRequestScreenViewModel(
   private val markAsNotFirstLaunch: MarkAsNotFirstLaunch,
   private val requestReadAudioFilesPermission: RequestReadAudioFilesPermission
): ViewModel() {

   private val _state = MutableStateFlow(PermissionRequestScreenState())
   val state = _state.asStateFlow()

   private val _navigationEvent = Channel<PermissionRequestScreenNavigationEvent>()
   val navigationEvent = _navigationEvent.receiveAsFlow()

   fun onEvent(event: PermissionRequestScreenEvent) {
      when (event) {
         is PermissionRequestScreenEvent.OnNextButtonClick -> onNextButtonClick()
         is PermissionRequestScreenEvent.HideAlertDialog -> onHideAlertDialog(event.alertDialog)
         is PermissionRequestScreenEvent.AskPermissionButtonClick -> onAskPermissionButtonClick(event.alertDialog)
         is PermissionRequestScreenEvent.PermissionIgnoreButtonClick -> onPermissionIgnoreButtonClick(event.alertDialog)
         is PermissionRequestScreenEvent.PermissionOpenSettingsButtonClick -> onPermissionOpenSettingsButtonClick()
      }
   }

   private fun onPermissionOpenSettingsButtonClick() {

   }

   private fun onPermissionIgnoreButtonClick(alertDialog: AlertDialog) {
      viewModelScope.launch {
         navigateToMusicListScreen()
         onHideAlertDialog(alertDialog)
      }
   }

   private fun onAskPermissionButtonClick(alertDialog: AlertDialog) {
      viewModelScope.launch {
         onHideAlertDialog(alertDialog)
         val permissionResult = requestReadAudioFilesPermission()
         when (permissionResult) {
            PermissionResult.GRANTED -> navigateToMusicListScreen()
            PermissionResult.DENIED -> showReadAudioFilesPermissionNeededAlertDialog()
            PermissionResult.DENIED_AND_DO_NOT_ASK_AGAIN -> showReadAudioFilesPermissionDeniedPermanentlyAlertDialog()
         }
      }
   }

   private fun onHideAlertDialog(dialog: AlertDialog) {
      _state.update { currentState ->
         currentState.copy(
            alertDialogs = currentState.alertDialogs.filterNot { it.id == dialog.id }
         )
      }
   }

   private fun onNextButtonClick() {
      viewModelScope.launch {
         val permissionResult = requestReadAudioFilesPermission()
         when (permissionResult) {
            PermissionResult.GRANTED -> navigateToMusicListScreen()
            PermissionResult.DENIED -> showReadAudioFilesPermissionNeededAlertDialog()
            PermissionResult.DENIED_AND_DO_NOT_ASK_AGAIN -> showReadAudioFilesPermissionDeniedPermanentlyAlertDialog()
         }
      }
   }

   private fun showReadAudioFilesPermissionDeniedPermanentlyAlertDialog() {
      _state.update { currentState ->
         currentState.copy(
            alertDialogs = currentState.alertDialogs + AlertDialog.ReadAudioFilesPermissionDeniedPermanentlyAlertDialog
         )
      }
   }

   private fun showReadAudioFilesPermissionNeededAlertDialog() {
      _state.update { currentState ->
         currentState.copy(
            alertDialogs = currentState.alertDialogs + AlertDialog.ReadAudioFilesPermissionNeededAlertDialog
         )
      }
   }

   private suspend fun navigateToMusicListScreen() {
      _navigationEvent.send(PermissionRequestScreenNavigationEvent.NavigateToMusicListScreen)
      markAsNotFirstLaunch()
   }

}
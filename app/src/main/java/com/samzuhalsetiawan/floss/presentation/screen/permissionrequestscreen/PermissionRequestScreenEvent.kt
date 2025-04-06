package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen

sealed class PermissionRequestScreenEvent {
   data object OnNextButtonClick : PermissionRequestScreenEvent()
   data class HideAlertDialog(val alertDialog: AlertDialog) : PermissionRequestScreenEvent()
   data class PermissionIgnoreButtonClick(val alertDialog: AlertDialog) : PermissionRequestScreenEvent()
   data object PermissionOpenSettingsButtonClick : PermissionRequestScreenEvent()
   data class AskPermissionButtonClick(val alertDialog: AlertDialog) : PermissionRequestScreenEvent()
}

sealed interface PermissionRequestScreenNavigationEvent {
   data object NavigateToMusicListScreen : PermissionRequestScreenNavigationEvent
}
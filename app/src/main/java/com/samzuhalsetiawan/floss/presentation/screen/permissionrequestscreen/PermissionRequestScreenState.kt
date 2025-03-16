package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen

class PermissionRequestScreenState

sealed class PermissionRequestScreenEvent {
   data object OnPermissionLauncherResult : PermissionRequestScreenEvent()

}

sealed interface PermissionRequestScreenNavigationEvent {
   data object NavigateToMusicListScreen : PermissionRequestScreenNavigationEvent
}
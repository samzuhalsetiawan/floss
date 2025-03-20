package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen

sealed class PermissionRequestScreenEvent {
   data object OnPermissionLauncherResult : PermissionRequestScreenEvent()

}

sealed interface PermissionRequestScreenNavigationEvent {
   data object NavigateToMusicListScreen : PermissionRequestScreenNavigationEvent
}
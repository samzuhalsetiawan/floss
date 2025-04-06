package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen

import java.util.UUID

data class PermissionRequestScreenState(
   val alertDialogs: List<AlertDialog> = emptyList()
)

sealed class AlertDialog(
   val id: String = UUID.randomUUID().toString()
) {
   data object ReadAudioFilesPermissionNeededAlertDialog : AlertDialog()
   data object ReadAudioFilesPermissionDeniedPermanentlyAlertDialog : AlertDialog()
}


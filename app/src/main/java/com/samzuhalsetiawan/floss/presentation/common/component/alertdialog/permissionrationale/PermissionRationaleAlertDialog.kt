package com.samzuhalsetiawan.floss.presentation.common.component.alertdialog.permissionrationale

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText

@Composable
fun PermissionRationaleAlertDialog(
   modifier: Modifier = Modifier,
   onGivePermissionButtonClick: () -> Unit,
   onIgnoreButtonClick: () -> Unit,
   onDismissRequest: () -> Unit,
) {
   AlertDialog(
      modifier = modifier,
      onDismissRequest = onDismissRequest,
      text = {
         NormalText(
            text = "You don't give permission to us to read audio files in your device, So we can only read the audio files inside Ringtone folder"
         )
      },
      confirmButton = {
         ConfirmButton(
            onIgnoreButtonClick = onIgnoreButtonClick,
            onGivePermissionButtonClick = onGivePermissionButtonClick
         )
      }
   )
}

@Composable
private fun ConfirmButton(
   modifier: Modifier = Modifier,
   onIgnoreButtonClick: () -> Unit,
   onGivePermissionButtonClick: () -> Unit
) {
   Row(
      modifier = modifier
   ) {
      TextButton(
         onClick = onIgnoreButtonClick
      ) {
         NormalText("Ignore")
      }
      TextButton(
         onClick = onGivePermissionButtonClick
      ) {
         NormalText("Give Permission")
      }
   }
}
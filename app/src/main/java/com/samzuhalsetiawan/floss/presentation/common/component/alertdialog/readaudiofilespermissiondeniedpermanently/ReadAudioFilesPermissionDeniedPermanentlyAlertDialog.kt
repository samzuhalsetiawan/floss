package com.samzuhalsetiawan.floss.presentation.common.component.alertdialog.readaudiofilespermissiondeniedpermanently

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.samzuhalsetiawan.floss.presentation.common.component.button.textbutton.TextButton
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText

@Composable
fun ReadAudioFilesPermissionDeniedPermanentlyAlertDialog(
   modifier: Modifier = Modifier,
   onDismissRequest: () -> Unit,
   onOpenSettingsButtonClick: () -> Unit,
   onIgnoreButtonClick: () -> Unit,
) {
   AlertDialog(
      modifier = modifier,
      onDismissRequest = onDismissRequest,
      properties = DialogProperties(
         dismissOnBackPress = false,
         dismissOnClickOutside = false
      ),
      text = {
         NormalText(
            text = "You don't give us permission to read your audio files, so cannot show your music list properly."
         )
      },
      confirmButton = {
         TextButton(
            text = "Open Settings",
            onClick = onOpenSettingsButtonClick
         )
      },
      dismissButton = {
         TextButton(
            text = "Ignore",
            onClick = onIgnoreButtonClick
         )
      },
   )
}
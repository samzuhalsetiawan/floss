package com.samzuhalsetiawan.floss.presentation.common.component.alertdialog.readaudiofilespermissionneeded

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.samzuhalsetiawan.floss.presentation.common.component.button.textbutton.TextButton
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText

@Composable
fun ReadAudioFilesPermissionNeededAlertDialog(
   modifier: Modifier = Modifier,
   onDismissRequest: () -> Unit,
   onGrantPermissionButtonClick: () -> Unit,
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
            text = "in order to read audio files in your device, you need to give us permission"
         )
      },
      confirmButton = {
         TextButton(
            text = "Grant Permission",
            onClick = onGrantPermissionButtonClick
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
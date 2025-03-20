package com.samzuhalsetiawan.floss.presentation.common.component.button.repeatbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode

@Composable
fun RepeatButton(
   modifier: Modifier = Modifier,
   repeatMode: RepeatMode,
   onClick: (RepeatMode) -> Unit
) {
   IconButton(
      modifier = modifier,
      onClick = {
         onClick(
            when (repeatMode) {
               RepeatMode.ONE -> RepeatMode.ALL
               RepeatMode.ALL -> RepeatMode.NONE
               RepeatMode.NONE -> RepeatMode.ONE
            }
         )
      }
   ) {
      Icon(
         imageVector = when (repeatMode) {
            RepeatMode.ONE -> Icons.Default.RepeatOne
            RepeatMode.ALL -> Icons.Default.Repeat
            RepeatMode.NONE -> Icons.Default.Repeat
         },
         contentDescription = null /* TODO: Add content description */,
         tint = if (repeatMode == RepeatMode.NONE) {
            LocalContentColor.current.copy(alpha = 0.3f)
         } else {
            LocalContentColor.current
         }
      )
   }
}
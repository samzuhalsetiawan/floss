package com.samzuhalsetiawan.floss.presentation.common.component.button.repeatbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.button.iconbutton.IconButton

enum class RepeatMode {
   SINGLE,
   ALL,
   OFF
}

@Composable
fun RepeatButton(
   modifier: Modifier = Modifier,
   repeatMode: RepeatMode
) {
   IconButton(
      modifier = modifier,
      icon = when (repeatMode) {
         RepeatMode.SINGLE -> Icons.Default.RepeatOne
         RepeatMode.ALL -> Icons.Default.Repeat
         RepeatMode.OFF -> Icons.Default.Repeat
      },
      contentDescription = null /* TODO: Add content description */,
      iconColor = if (repeatMode == RepeatMode.OFF) {
         LocalContentColor.current.copy(alpha = 0.3f)
      } else {
         LocalContentColor.current
      }
   )
}
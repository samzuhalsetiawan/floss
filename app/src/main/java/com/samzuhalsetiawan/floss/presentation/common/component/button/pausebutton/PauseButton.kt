package com.samzuhalsetiawan.floss.presentation.common.component.button.pausebutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PauseButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit = {}
) {
   IconButton(
      modifier = modifier,
      onClick = onClick
   ) {
      Icon(
         imageVector = Icons.Default.Pause,
         contentDescription = null /* TODO: Add content description */,
      )
   }
}
package com.samzuhalsetiawan.floss.presentation.common.component.button.shufflebutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ShuffleButton(
   modifier: Modifier = Modifier,
   isShuffleOn: Boolean,
   onClick: (Boolean) -> Unit
) {
   IconButton(
      modifier = modifier,
      onClick = {
         onClick(!isShuffleOn)
      }
   ) {
      Icon(
         imageVector = Icons.Default.Shuffle,
         contentDescription = null /* TODO: Add content description */,
         tint = if (isShuffleOn) {
            LocalContentColor.current
         } else {
            LocalContentColor.current.copy(alpha = 0.3f)
         }
      )
   }
}
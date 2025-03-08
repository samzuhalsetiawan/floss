package com.samzuhalsetiawan.floss.presentation.common.component.button.shufflebutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.button.iconbutton.IconButton

@Composable
fun ShuffleButton(
   modifier: Modifier = Modifier,
   isShuffleOn: Boolean,
) {
   IconButton(
      modifier = modifier,
      icon = Icons.Default.Shuffle,
      contentDescription = null /* TODO: Add content description */,
      iconColor = if (isShuffleOn) {
         LocalContentColor.current
      } else {
         LocalContentColor.current.copy(alpha = 0.3f)
      }
   )
}
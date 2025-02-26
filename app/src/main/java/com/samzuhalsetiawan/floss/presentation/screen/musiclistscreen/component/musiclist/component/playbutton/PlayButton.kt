package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.component.playbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.iconbutton.IconButton

@Composable
fun PlayButton(
   modifier: Modifier = Modifier,
) {
   IconButton(
      modifier = modifier,
      icon = Icons.Default.PlayArrow,
      contentDescription = null /* TODO: Add content description */
   )
}
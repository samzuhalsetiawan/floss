package com.samzuhalsetiawan.floss.presentation.common.component.button.nextbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.button.iconbutton.IconButton

@Composable
fun NextButton(
   modifier: Modifier = Modifier
) {
   IconButton(
      modifier = modifier,
      icon = Icons.Default.SkipNext,
      contentDescription = null /* TODO: Add content description */
   )
}
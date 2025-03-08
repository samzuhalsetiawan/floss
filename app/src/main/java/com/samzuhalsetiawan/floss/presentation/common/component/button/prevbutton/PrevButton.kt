package com.samzuhalsetiawan.floss.presentation.common.component.button.prevbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.button.iconbutton.IconButton

@Composable
fun PrevButton(
   modifier: Modifier = Modifier
) {
   IconButton(
      modifier = modifier,
      icon = Icons.Default.SkipPrevious,
      contentDescription = null /* TODO: Add content description */
   )
}
package com.samzuhalsetiawan.floss.presentation.common.component.button.prevbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PrevButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit = {}
) {
   IconButton(
      modifier = modifier,
      onClick = onClick
   ) {
      Icon(
         imageVector = Icons.Default.SkipPrevious,
         contentDescription = null /* TODO: Add content description */
      )
   }
}
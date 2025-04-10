package com.samzuhalsetiawan.floss.presentation.common.component.button.nextbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NextButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit = {}
) {
   IconButton(
      modifier = modifier,
      onClick = onClick
   ) {
      Icon(
         imageVector = Icons.Default.SkipNext,
         contentDescription = null /* TODO: Add content description */
      )
   }
}
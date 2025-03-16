package com.samzuhalsetiawan.floss.presentation.common.component.button.expanddownbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpandDownButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit
) {
   IconButton(
      modifier = modifier,
      onClick = onClick
   ) {
      Icon(
         imageVector = Icons.Default.KeyboardArrowDown,
         contentDescription = null /* TODO: Add content description */
      )
   }
}
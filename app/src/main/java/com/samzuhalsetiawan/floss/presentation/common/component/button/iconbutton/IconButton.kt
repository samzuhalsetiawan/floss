package com.samzuhalsetiawan.floss.presentation.common.component.button.iconbutton

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButton(
   modifier: Modifier = Modifier,
   icon: ImageVector,
   contentDescription: String?,
   iconColor: Color = LocalContentColor.current,
   onClick: () -> Unit = {}
) {
   androidx.compose.material3.IconButton(
      modifier = modifier,
      onClick = onClick
   ) {
      Icon(
         imageVector = icon,
         contentDescription = contentDescription,
         tint = iconColor
      )
   }
}
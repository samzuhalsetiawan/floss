package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.component.illustration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Illustration(
   modifier: Modifier = Modifier
) {
   Box(
      modifier = modifier
         .background(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium
         )
   )
}
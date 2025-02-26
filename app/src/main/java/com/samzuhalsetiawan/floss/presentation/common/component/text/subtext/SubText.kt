package com.samzuhalsetiawan.floss.presentation.common.component.text.subtext

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SubText(
   text: String,
   modifier: Modifier = Modifier,
) {
   Text(
      modifier = modifier,
      text = text,
      style = MaterialTheme.typography.bodySmall
   )
}
package com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NormalText(
   text: String,
   modifier: Modifier = Modifier,
) {
   Text(
      modifier = modifier,
      text = text,
   )
}
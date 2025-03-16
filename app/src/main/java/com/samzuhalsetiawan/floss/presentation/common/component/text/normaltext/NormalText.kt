package com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NormalText(
   text: String,
   modifier: Modifier = Modifier,
   textAlign: TextAlign? = null
) {
   Text(
      modifier = modifier,
      text = text,
      textAlign = textAlign
   )
}
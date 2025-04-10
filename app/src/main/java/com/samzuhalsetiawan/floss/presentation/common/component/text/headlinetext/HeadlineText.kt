package com.samzuhalsetiawan.floss.presentation.common.component.text.headlinetext

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HeadlineText(
   text: String,
   modifier: Modifier = Modifier,
   textAlign: TextAlign? = null,
   fontStyle: FontStyle? = null,
   fontWeight: FontWeight? = null,
) {
   Text(
      modifier = modifier,
      text = text,
      style = MaterialTheme.typography.headlineMedium,
      textAlign = textAlign,
      fontStyle = fontStyle,
      fontWeight = fontWeight
   )
}
package com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NormalText(
   text: String,
   modifier: Modifier = Modifier,
   textAlign: TextAlign? = null,
   fontStyle: FontStyle? = null,
   fontWeight: FontWeight? = null,
) {
   Text(
      modifier = modifier,
      text = text,
      textAlign = textAlign,
      fontStyle = fontStyle,
      fontWeight = fontWeight
   )
}
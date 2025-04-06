package com.samzuhalsetiawan.floss.presentation.common.component.button.textbutton

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText

@Composable
fun TextButton(
   modifier: Modifier = Modifier,
   text: String,
   onClick: () -> Unit,
) {
   TextButton(
      modifier = modifier,
      onClick = onClick
   ) {
      NormalText(text = text)
   }
}
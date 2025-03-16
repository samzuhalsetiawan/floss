package com.samzuhalsetiawan.floss.presentation.common.component.button.normalbutton

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText

@Composable
fun NormalButton(
   text: String,
   modifier: Modifier = Modifier,
   onClick: () -> Unit
) {
   Button(
      modifier = modifier,
      onClick = onClick
   ) {
      NormalText(text)
   }
}
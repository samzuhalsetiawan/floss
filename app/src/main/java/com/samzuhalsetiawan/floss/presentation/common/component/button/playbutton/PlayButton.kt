package com.samzuhalsetiawan.floss.presentation.common.component.button.playbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samzuhalsetiawan.floss.presentation.common.component.button.iconbutton.IconButton
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme


@Composable
fun PlayButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit = {}
) {
   IconButton(
      modifier = modifier,
      icon = Icons.Default.PlayArrow,
      contentDescription = null /* TODO: Add content description */,
      onClick = onClick
   )
}

@Composable
fun PlayButtonLarge(
   modifier: Modifier = Modifier,
) {
   FilledIconButton(
      modifier = modifier,
      onClick = {

      }
   ) {
      Icon(
         imageVector = Icons.Default.PlayArrow,
         contentDescription = null /* TODO: Add content description */
      )
   }
}

@Preview(showBackground = true)
@Composable
private fun PlayButtonPreview() {
   FlossTheme {
      PlayButtonLarge()
   }
}

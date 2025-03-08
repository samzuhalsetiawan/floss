package com.samzuhalsetiawan.floss.presentation.common.component.albumart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun AlbumArt(
   modifier: Modifier = Modifier,
) {
   Box(
      modifier = modifier
         .background(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium
         )
   )
}


@Composable
fun AlbumArtSmall(
   modifier: Modifier = Modifier
) {
   Box(
      modifier = modifier
         .background(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small
         )
   )
}

@Preview(showBackground = true)
@Composable
private fun AlbumArtPreview() {
   FlossTheme {
      Box(
         modifier = Modifier.fillMaxWidth(),
         contentAlignment = Alignment.Center
      ) {
         AlbumArt(
            modifier = Modifier
               .size(200.dp)
               .padding(14.dp)
         )
      }
   }
}

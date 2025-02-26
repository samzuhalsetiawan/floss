package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.component.albumart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MiniAlbumArt(
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
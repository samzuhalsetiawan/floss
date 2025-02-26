package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samzuhalsetiawan.floss.domain.util.dummy.DummyData
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.MusicList
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun MusicListScreen() {
   Scaffold { scaffoldPadding ->
      Column(
         modifier = Modifier.padding(scaffoldPadding)
      ) {
         LazyColumn(
            modifier = Modifier.fillMaxSize()
         ) {
            items(
               items = DummyData.musics,
               key = { music -> music.id },
            ) {
               MusicList(music = it)
            }
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun MusicListScreenPreview() {
   FlossTheme {
      MusicListScreen()
   }
}
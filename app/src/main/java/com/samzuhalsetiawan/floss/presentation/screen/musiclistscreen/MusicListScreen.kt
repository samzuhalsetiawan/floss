package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samzuhalsetiawan.floss.domain.util.dummy.DummyData
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.MusicList
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun MusicListScreen(
   viewModel: MusicListScreenVM
) {
   val state by viewModel.state.collectAsStateWithLifecycle()

   MusicListScreen(
      state = state,
      onEvent = viewModel::onEvent
   )
}

@Composable
private fun MusicListScreen(
   state: MusicListScreenState,
   onEvent: (MusicListScreenEvent) -> Unit
) {
   Scaffold { scaffoldPadding ->
      Column(
         modifier = Modifier.padding(scaffoldPadding)
      ) {
         if (state.isLoading) {
            Box(
               modifier = Modifier.fillMaxSize(),
               contentAlignment = Alignment.Center
            ) {
               CircularProgressIndicator()
            }
         } else {
            LazyColumn(
               modifier = Modifier.fillMaxSize()
            ) {
               items(
                  items = state.musics,
                  key = { music -> music.id },
               ) {
                  MusicList(music = it)
               }
            }
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun MusicListScreenPreview() {
   FlossTheme {
      MusicListScreen(
         state = MusicListScreenState(),
         onEvent = {}
      )
   }
}
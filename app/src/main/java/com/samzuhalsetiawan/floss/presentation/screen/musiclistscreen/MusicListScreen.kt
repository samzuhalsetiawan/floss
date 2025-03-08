package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
   Scaffold(
      contentWindowInsets = WindowInsets(0,0,0,0)
   ) { scaffoldPadding ->
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
            var currentMusicId by remember { mutableStateOf<String?>(null) }

            LazyColumn(
               modifier = Modifier.fillMaxSize()
            ) {
               items(
                  items = state.musics,
                  key = { music -> music.id },
               ) {
                  MusicList(
                     modifier = Modifier
                        .then(
                           if (it.id == currentMusicId) {
                              Modifier.padding(14.dp)
                           } else {
                              Modifier
                           }
                        ),
                     expanded = it.id == currentMusicId,
                     music = it,
                     onPlayButtonClick = {
                        currentMusicId = if (currentMusicId == it.id) null else it.id
                     }
                  )
                  AnimatedVisibility(
                     visible = it.id != currentMusicId
                  ) {
                     HorizontalDivider(
                        modifier = Modifier
                           .padding(horizontal = 12.dp, vertical = 4.dp)
                     )
                  }
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
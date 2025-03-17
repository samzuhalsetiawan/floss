package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samzuhalsetiawan.floss.presentation.common.component.alertbar.missingpermissionbar.MissingPermissionBar
import com.samzuhalsetiawan.floss.presentation.common.util.getActivity
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.MusicList
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun MusicListScreen(
   viewModel: MusicListScreenViewModel
) {
   val context = LocalContext.current
   val state by viewModel.state.collectAsStateWithLifecycle()


   LaunchedEffect(Unit) {
      viewModel.onEvent(MusicListScreenEvent.OnChangePermissionStatus(checkPermissionStatus(context)))
   }

   MusicListScreen(
      state = state,
      onEvent = viewModel::onEvent
   )
//   for (alertDialog in state.alertDialogs) {
//      when (alertDialog) {
//
//      }
//   }
}

@Composable
private fun MusicListScreen(
   state: MusicListScreenState,
   onEvent: (MusicListScreenEvent) -> Unit
) {
   val readMediaAudioPermissionLauncher = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.RequestPermission(),
      onResult = { isGranted ->
         if (isGranted) {
            onEvent(MusicListScreenEvent.UpdateMusicList)
            onEvent(MusicListScreenEvent.HideMissingPermissionBar)
         } else {
            onEvent(MusicListScreenEvent.ShowMissingPermissionBar)
         }
      }
   )

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

            if (state.showMissingPermissionBar) {
               var expanded by remember { mutableStateOf(false) }

               MissingPermissionBar(
                  expanded = expanded,
                  isFullDenied = state.permissionStatus == PermissionStatus.DENIED,
                  description = "You don't give permission to us to read audio files in your device, So we can only read the audio files inside Ringtone folder",
                  onExpandButtonClick = { expanded = !expanded },
                  onActionButtonClick = {
                     if (state.permissionStatus == PermissionStatus.HALF_DENIED) {
                        readMediaAudioPermissionLauncher.launch(readAudiFilesPermission)
                     }
                  },
                  onDismissRequest = {
                     onEvent(MusicListScreenEvent.HideMissingPermissionBar)
                     expanded = false
                  },
               )
            }
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
                        if (it.id != currentMusicId) {
                           onEvent(MusicListScreenEvent.OnPlayButtonClick(it))
                        } else {
                           onEvent(MusicListScreenEvent.OnPauseButtonClick(it))
                        }
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

private val readAudiFilesPermission =
   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      android.Manifest.permission.READ_MEDIA_AUDIO
   } else {
      android.Manifest.permission.READ_EXTERNAL_STORAGE
   }

private fun checkPermissionStatus(
   context: Context,
): PermissionStatus {
   val activity = context.getActivity() ?: throw Exception("Activity not found")

   val isReadAudioFilesPermissionGranted =
      ContextCompat.checkSelfPermission(context, readAudiFilesPermission) == PackageManager.PERMISSION_GRANTED

   val shouldShowRequestPermissionRationale = activity.shouldShowRequestPermissionRationale(readAudiFilesPermission)

   return when {
      isReadAudioFilesPermissionGranted -> PermissionStatus.GRANTED
      shouldShowRequestPermissionRationale -> PermissionStatus.HALF_DENIED
      else -> PermissionStatus.DENIED
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
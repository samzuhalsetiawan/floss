package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samzuhalsetiawan.floss.presentation.common.component.alertbar.missingpermissionbar.MissingPermissionBar
import com.samzuhalsetiawan.floss.presentation.common.util.asBitmap
import com.samzuhalsetiawan.floss.presentation.common.util.getActivity
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.MusicList
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme
import kotlin.math.roundToInt

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
   val density = LocalDensity.current

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
            val lazyColumnState = rememberLazyListState()
            val layoutInfo by remember { derivedStateOf { lazyColumnState.layoutInfo } }
            LaunchedEffect(lazyColumnState, layoutInfo) {
               if (state.currentMusic == null) return@LaunchedEffect
               val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull() ?: return@LaunchedEffect
               val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@LaunchedEffect
               val currentActiveMusicIndex = state.musics.indexOf(state.currentMusic)
               when {
                  firstVisibleItem.index == currentActiveMusicIndex + 1 -> {
                     onEvent(MusicListScreenEvent.ShowTopFloatingMusicListItem)
                  }
                  lastVisibleItem.index == currentActiveMusicIndex - 1 -> {
                     onEvent(MusicListScreenEvent.ShowBottomFloatingMusicListItem)
                  }
                  firstVisibleItem.index == currentActiveMusicIndex || lastVisibleItem.index == currentActiveMusicIndex -> {
                     onEvent(MusicListScreenEvent.HideFloatingMusicListItem)
                  }
               }
            }

            Box {
               LazyColumn(
                  modifier = Modifier.fillMaxSize(),
                  state = lazyColumnState
               ) {
                  items(
                     items = state.musics,
                     key = { music -> music.id },
                  ) { music ->
                     MusicList(
                        modifier = Modifier
                           .then(
                              if (music.id == state.currentMusic?.id) {
                                 Modifier
                                    .padding(14.dp)
                              } else {
                                 Modifier
                              }
                           ),
                        expanded = music.id == state.currentMusic?.id,
                        music = music,
                        isShuffleOn = state.isShuffleModeActive,
                        isPlaying = state.isPlaying,
                        repeatMode = state.repeatMode,
                        onPlayButtonClick = {
                           if (music.id != state.currentMusic?.id) {
                              onEvent(MusicListScreenEvent.OnPlayButtonClick(music))
                           } else {
                              if (!state.isPlaying) {
                                 onEvent(MusicListScreenEvent.OnResumeButtonClick)
                              }
                           }
                        },
                        onPauseButtonClick = {
                           onEvent(MusicListScreenEvent.OnPauseButtonClick)
                        },
                        onPrevButtonClick = {
                           onEvent(MusicListScreenEvent.OnPrevButtonClick)
                        },
                        onNextButtonClick = {
                           onEvent(MusicListScreenEvent.OnNextButtonClick)
                        },
                        onShuffleButtonClick = {
                           onEvent(MusicListScreenEvent.OnShuffleButtonClick(it))
                        },
                        onRepeatButtonClick = {
                           onEvent(MusicListScreenEvent.OnRepeatButtonClick(it))
                        },
                        onMoreOptionButtonClick = {},
                        onFullscreenButtonClick = {}
                     )
                     androidx.compose.animation.AnimatedVisibility(
                        visible = music.id != state.currentMusic?.id
                     ) {
                        HorizontalDivider(
                           modifier = Modifier
                              .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                     }
                  }
               }
               androidx.compose.animation.AnimatedVisibility(
                  modifier = Modifier.align(Alignment.TopCenter),
                  visible = state.showTopFloatingMusicListItem,
                  enter = slideInVertically {
                     -it / 2
                  } + expandVertically(
                     expandFrom = Alignment.Top
                  ),
                  exit = slideOutVertically {
                     -it / 2
                  } + shrinkVertically(
                     shrinkTowards = Alignment.Top
                  )
               ) {
                  if (state.currentMusic == null) return@AnimatedVisibility
                  MusicList(
                     modifier = Modifier.padding(14.dp),
                     expanded = true,
                     music = state.currentMusic,
                     isShuffleOn = state.isShuffleModeActive,
                     isPlaying = state.isPlaying,
                     repeatMode = state.repeatMode,
                     onPlayButtonClick = {
                        if (!state.isPlaying) {
                           onEvent(MusicListScreenEvent.OnResumeButtonClick)
                        } else {
                           onEvent(MusicListScreenEvent.OnPlayButtonClick(state.currentMusic))
                        }
                     },
                     onPauseButtonClick = {
                        onEvent(MusicListScreenEvent.OnPauseButtonClick)
                     },
                     onPrevButtonClick = {
                        onEvent(MusicListScreenEvent.OnPrevButtonClick)
                     },
                     onNextButtonClick = {
                        onEvent(MusicListScreenEvent.OnNextButtonClick)
                     },
                     onShuffleButtonClick = {
                        onEvent(MusicListScreenEvent.OnShuffleButtonClick(it))
                     },
                     onRepeatButtonClick = {
                        onEvent(MusicListScreenEvent.OnRepeatButtonClick(it))
                     },
                     onMoreOptionButtonClick = {},
                     onFullscreenButtonClick = {}
                  )
               }
               androidx.compose.animation.AnimatedVisibility(
                  modifier = Modifier.align(Alignment.BottomCenter),
                  visible = state.showBottomFloatingMusicListItem,
                  enter = slideInVertically {
                     it / 2
                  } + expandVertically(
                     expandFrom = Alignment.Bottom
                  ),
                  exit = slideOutVertically {
                     it / 2
                  } + shrinkVertically(
                     shrinkTowards = Alignment.Bottom
                  )
               ) {
                  if (state.currentMusic == null) return@AnimatedVisibility
                  MusicList(
                     modifier = Modifier.padding(14.dp),
                     expanded = true,
                     music = state.currentMusic,
                     isShuffleOn = state.isShuffleModeActive,
                     isPlaying = state.isPlaying,
                     repeatMode = state.repeatMode,
                     onPlayButtonClick = {
                        if (!state.isPlaying) {
                           onEvent(MusicListScreenEvent.OnResumeButtonClick)
                        } else {
                           onEvent(MusicListScreenEvent.OnPlayButtonClick(state.currentMusic))
                        }
                     },
                     onPauseButtonClick = {
                        onEvent(MusicListScreenEvent.OnPauseButtonClick)
                     },
                     onPrevButtonClick = {
                        onEvent(MusicListScreenEvent.OnPrevButtonClick)
                     },
                     onNextButtonClick = {
                        onEvent(MusicListScreenEvent.OnNextButtonClick)
                     },
                     onShuffleButtonClick = {
                        onEvent(MusicListScreenEvent.OnShuffleButtonClick(it))
                     },
                     onRepeatButtonClick = {
                        onEvent(MusicListScreenEvent.OnRepeatButtonClick(it))
                     },
                     onMoreOptionButtonClick = {},
                     onFullscreenButtonClick = {}
                  )
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
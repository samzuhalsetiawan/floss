package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samzuhalsetiawan.floss.R
import com.samzuhalsetiawan.floss.presentation.common.component.alertbar.missingpermissionbar.MissingPermissionBar
import com.samzuhalsetiawan.floss.presentation.common.component.alertdialog.readaudiofilespermissiondeniedpermanently.ReadAudioFilesPermissionDeniedPermanentlyAlertDialog
import com.samzuhalsetiawan.floss.presentation.common.component.layout.layoutwithloadingindicator.LayoutWithLoadingIndicator
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.layoutwithfloatingmusiclistitem.MusicListWithFloatingMusicListItem
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.MusicList
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.bottomFloatingMusicListItemEnterTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.bottomFloatingMusicListItemExitTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.topFloatingMusicListItemEnterTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.topFloatingMusicListItemExitTransition
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun MusicListScreen(
   viewModel: MusicListScreenViewModel
) {
   val state by viewModel.state.collectAsStateWithLifecycle()

   MusicListScreen(
      state = state,
      onEvent = viewModel::onEvent
   )

   for (alertDialog in state.alertDialogs) {
      when (alertDialog) {
         is AlertDialog.ReadAudioFilesPermissionDeniedPermanently -> {
            ReadAudioFilesPermissionDeniedPermanentlyAlertDialog(
               onDismissRequest = { viewModel.onEvent(MusicListScreenEvent.OnPermissionDeniedPermanentlyAlertDialogIgnoreButtonClick) },
               onIgnoreButtonClick = { viewModel.onEvent(MusicListScreenEvent.OnPermissionDeniedPermanentlyAlertDialogIgnoreButtonClick) },
               onOpenSettingsButtonClick = { viewModel.onEvent(MusicListScreenEvent.OnPermissionDeniedPermanentlyAlertDialogOpenSettingsButtonClick) }
            )
         }
      }
   }
}

@Composable
private fun MusicListScreen(
   state: MusicListScreenState,
   onEvent: (MusicListScreenEvent) -> Unit
) {

   LayoutWithLoadingIndicator(
      isLoading = state.isLoading,
      modifier = Modifier.fillMaxSize()
   ) {
      Column {
         AnimatedVisibility(
            visible = state.isMissingPermissionBarShowed
         ) {
            MissingPermissionBar(
               expanded = state.isMissingPermissionBarExpanded,
               description = stringResource(R.string.read_audio_files_missing_permission_bar_description),
               onExpandButtonClick = { onEvent(MusicListScreenEvent.OnMissingPermissionBarExpandButtonClick) },
               onActionButtonClick = { onEvent(MusicListScreenEvent.OnMissingPermissionBarGrantPermissionButtonClick) },
               onDismissRequest = { onEvent(MusicListScreenEvent.OnMissingPermissionBarDismissButtonClick) },
            )
         }
         MusicListWithFloatingMusicListItem(
            state = state,
            onEvent = onEvent,
            musics = state.musics,
         ) { music ->
            MusicList(
               expanded = music.id == state.currentMusic?.id,
               music = music,
               isShuffleOn = state.isShuffleModeActive,
               isPlaying = state.isPlaying,
               repeatMode = state.repeatMode,
               onPlayButtonClick = { onEvent(MusicListScreenEvent.OnMusicListItemPlayButtonClick(music)) },
               onPauseButtonClick = { onEvent(MusicListScreenEvent.OnPauseButtonClick) },
               onPrevButtonClick = { onEvent(MusicListScreenEvent.OnPrevButtonClick) },
               onNextButtonClick = { onEvent(MusicListScreenEvent.OnNextButtonClick) },
               onShuffleButtonClick = { onEvent(MusicListScreenEvent.OnShuffleButtonClick(it)) },
               onRepeatButtonClick = { onEvent(MusicListScreenEvent.OnRepeatButtonClick(it)) },
               onMoreOptionButtonClick = {},
               onFullscreenButtonClick = {}
            )
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
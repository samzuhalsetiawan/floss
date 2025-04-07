package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.presentation.common.model.Music

sealed class MusicListScreenEvent {
   data object OnMissingPermissionBarGrantPermissionButtonClick: MusicListScreenEvent()
   data object OnMissingPermissionBarDismissButtonClick: MusicListScreenEvent()
   data object OnPermissionDeniedPermanentlyAlertDialogOpenSettingsButtonClick: MusicListScreenEvent()
   data object OnPermissionDeniedPermanentlyAlertDialogIgnoreButtonClick: MusicListScreenEvent()
   data class OnMusicListItemPlayButtonClick(val music: Music?): MusicListScreenEvent()
   data object OnPauseButtonClick: MusicListScreenEvent()
   data object OnNextButtonClick: MusicListScreenEvent()
   data object OnPrevButtonClick: MusicListScreenEvent()
   data class OnShuffleButtonClick(val isActive: Boolean): MusicListScreenEvent()
   data class OnRepeatButtonClick(val repeatMode: RepeatMode): MusicListScreenEvent()
}
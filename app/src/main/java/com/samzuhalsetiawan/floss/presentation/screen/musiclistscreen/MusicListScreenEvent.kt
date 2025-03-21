package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.presentation.common.model.Music

sealed class MusicListScreenEvent {
   data object UpdateMusicList: MusicListScreenEvent()
   data object  ShowMissingPermissionBar: MusicListScreenEvent()
   data object HideMissingPermissionBar: MusicListScreenEvent()
   data class ShowAlertDialog(val alertDialog: AlertDialog): MusicListScreenEvent()
   data class HideAlertDialog(val alertDialog: AlertDialog): MusicListScreenEvent()
   data class OnChangePermissionStatus(val permissionStatus: PermissionStatus): MusicListScreenEvent()
   data class OnPlayButtonClick(val music: Music): MusicListScreenEvent()
   data object OnResumeButtonClick: MusicListScreenEvent()
   data object OnPauseButtonClick: MusicListScreenEvent()
   data object OnNextButtonClick: MusicListScreenEvent()
   data object OnPrevButtonClick: MusicListScreenEvent()
   data class OnShuffleButtonClick(val isActive: Boolean): MusicListScreenEvent()
   data class OnRepeatButtonClick(val repeatMode: RepeatMode): MusicListScreenEvent()
}
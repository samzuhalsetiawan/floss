package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import com.samzuhalsetiawan.floss.domain.model.Music

data class MusicListScreenState(
   val isLoading: Boolean = true,
   val musics: List<Music> = emptyList(),
   val showMissingPermissionBar: Boolean = false,
   val permissionStatus: PermissionStatus = PermissionStatus.GRANTED,
   val alertDialogs: List<AlertDialog> = emptyList()
)

sealed class MusicListScreenEvent {
   data object UpdateMusicList: MusicListScreenEvent()
   data object  ShowMissingPermissionBar: MusicListScreenEvent()
   data object HideMissingPermissionBar: MusicListScreenEvent()
   data class ShowAlertDialog(val alertDialog: AlertDialog): MusicListScreenEvent()
   data class HideAlertDialog(val alertDialog: AlertDialog): MusicListScreenEvent()
   data class OnChangePermissionStatus(val permissionStatus: PermissionStatus): MusicListScreenEvent()
   data class OnPlayButtonClick(val music: Music): MusicListScreenEvent()
   data class OnPauseButtonClick(val music: Music): MusicListScreenEvent()
}

sealed class AlertDialog

enum class PermissionStatus {
   GRANTED, HALF_DENIED, DENIED
}
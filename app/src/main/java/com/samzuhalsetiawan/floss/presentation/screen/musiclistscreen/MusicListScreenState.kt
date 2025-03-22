package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.presentation.common.model.Music

data class MusicListScreenState(
   val isLoading: Boolean = true,
   val musics: List<Music> = emptyList(),
   val currentMusic: Music? = null,
   val isPlaying: Boolean = false,
   val isShuffleModeActive: Boolean = false,
   val repeatMode: RepeatMode = RepeatMode.NONE,
   val showMissingPermissionBar: Boolean = false,
   val permissionStatus: PermissionStatus = PermissionStatus.GRANTED,
   val alertDialogs: List<AlertDialog> = emptyList(),
   val showTopFloatingMusicListItem: Boolean = false,
   val showBottomFloatingMusicListItem: Boolean = false
)

sealed class AlertDialog

enum class PermissionStatus {
   GRANTED, HALF_DENIED, DENIED
}
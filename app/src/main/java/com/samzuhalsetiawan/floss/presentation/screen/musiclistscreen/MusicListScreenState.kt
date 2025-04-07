package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.presentation.common.model.Music

data class MusicListScreenState(
   val isLoading: Boolean = true,
   val isMissingPermissionBarShowed: Boolean = false,
   val musics: List<Music> = emptyList(),
   val currentMusic: Music? = null,
   val isPlaying: Boolean = false,
   val isShuffleModeActive: Boolean = false,
   val repeatMode: RepeatMode = RepeatMode.NONE,
   val alertDialogs: List<AlertDialog> = emptyList(),
)

sealed class AlertDialog {
   data object ReadAudioFilesPermissionDeniedPermanently: AlertDialog()
}
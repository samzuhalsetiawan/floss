package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicListScreenViewModel(
   private val musicRepository: MusicRepository,
   private val mediaController: MediaController
): ViewModel() {

   private val _state = MutableStateFlow(MusicListScreenState())
   val state = _state.asStateFlow()

   fun onEvent(event: MusicListScreenEvent) {
      when (event) {
         is MusicListScreenEvent.HideAlertDialog -> onHideAlertDialog(event.alertDialog)
         is MusicListScreenEvent.ShowAlertDialog -> onShowAlertDialog(event.alertDialog)
         is MusicListScreenEvent.UpdateMusicList -> onUpdateMusicList()
         is MusicListScreenEvent.HideMissingPermissionBar -> onHideMissingPermissionBar()
         is MusicListScreenEvent.ShowMissingPermissionBar -> onShowMissingPermissionBar()
         is MusicListScreenEvent.OnChangePermissionStatus -> onChangePermissionStatus(event.permissionStatus)
         is MusicListScreenEvent.OnPauseButtonClick -> onPauseButtonClick(event.music)
         is MusicListScreenEvent.OnPlayButtonClick -> onPlayButtonClick(event.music)
      }
   }

   private fun onPauseButtonClick(music: Music) {
      mediaController.pause()
   }

   private fun onPlayButtonClick(music: Music) {
      val mediaItem = MediaItem.fromUri(music.uri)
      mediaController.setMediaItem(mediaItem)
      mediaController.prepare()
      mediaController.play()
   }

   private fun onChangePermissionStatus(status: PermissionStatus) {
      _state.update {
         it.copy(
            permissionStatus = status,
            showMissingPermissionBar = status != PermissionStatus.GRANTED
         )
      }
   }

   private fun onHideMissingPermissionBar() {
      _state.update { it.copy(showMissingPermissionBar = false) }
   }

   private fun onShowMissingPermissionBar() {
      _state.update { it.copy(showMissingPermissionBar = true) }
   }

   private fun onUpdateMusicList() {
      getAllMusics()
   }

   private fun onHideAlertDialog(alertDialog: AlertDialog) {
      _state.update { currentState ->
         currentState.copy(alertDialogs = currentState.alertDialogs - alertDialog)
      }
   }

   private fun onShowAlertDialog(alertDialog: AlertDialog) {
      _state.update {
         it.copy(alertDialogs = it.alertDialogs + alertDialog)
      }
   }

   init {
      getAllMusics()
   }

   private fun getAllMusics() {
      viewModelScope.launch(Dispatchers.IO) {
         _state.update { it.copy(isLoading = true) }
         musicRepository.getAllMusics()
            .onSuccess { musics ->
               _state.update {
                  it.copy(isLoading = false, musics = musics)
               }
            }
            .onFailure { throw it }
      }
   }

}
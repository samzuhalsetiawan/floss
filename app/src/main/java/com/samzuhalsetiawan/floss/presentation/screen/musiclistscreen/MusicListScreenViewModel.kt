package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import com.samzuhalsetiawan.floss.presentation.common.component.button.repeatbutton.RepeatMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicListScreenViewModel(
   private val musicRepository: MusicRepository,
   private val mediaController: MediaController
): ViewModel(), Player.Listener {

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
         is MusicListScreenEvent.OnPauseButtonClick -> onPauseButtonClick()
         is MusicListScreenEvent.OnPlayButtonClick -> onPlayButtonClick(event.music)
         is MusicListScreenEvent.OnNextButtonClick -> onNextButtonClick()
         is MusicListScreenEvent.OnPrevButtonClick -> onPrevButtonClick()
         is MusicListScreenEvent.OnRepeatButtonClick -> onRepeatButtonClick(event.repeatMode)
         is MusicListScreenEvent.OnShuffleButtonClick -> onShuffleButtonClick(event.isActive)
      }
   }

   init {
      getAllMusics()
      mediaController.addListener(this)
   }

   override fun onIsPlayingChanged(isPlaying: Boolean) {
      _state.update { currentState ->
         currentState.copy(isPlaying = isPlaying)
      }
   }

   override fun onRepeatModeChanged(repeatMode: Int) {
      _state.update { currentState ->
         currentState.copy(
            repeatMode = when (repeatMode) {
               Player.REPEAT_MODE_OFF -> RepeatMode.OFF
               Player.REPEAT_MODE_ALL -> RepeatMode.ALL
               Player.REPEAT_MODE_ONE -> RepeatMode.SINGLE
               else -> throw Exception("Unknown repeat mode: $repeatMode")
            }
         )
      }
   }

   override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
      _state.update { currentState ->
         currentState.copy(isShuffleModeActive = shuffleModeEnabled)
      }
   }

   private fun onShuffleButtonClick(isActive: Boolean) {
      mediaController.shuffleModeEnabled = isActive
   }

   private fun onRepeatButtonClick(repeatMode: RepeatMode) {
      mediaController.repeatMode = when (repeatMode) {
         RepeatMode.OFF -> Player.REPEAT_MODE_OFF
         RepeatMode.ALL -> Player.REPEAT_MODE_ALL
         RepeatMode.SINGLE -> Player.REPEAT_MODE_ONE
      }
   }

   private fun onPrevButtonClick() {
      mediaController.seekToPrevious()
   }

   private fun onNextButtonClick() {
      mediaController.seekToNext()
   }

   private fun onPauseButtonClick() {
      mediaController.pause()
   }

   private fun onPlayButtonClick(music: Music) {
      if (_state.value.currentMusic == music) return resumeMusic()
      _state.update { currentState ->
         currentState.copy(currentMusic = music)
      }
      val mediaItem = MediaItem.fromUri(music.uri)
      mediaController.setMediaItem(mediaItem)
      mediaController.prepare()
      mediaController.play()
   }

   private fun resumeMusic() {
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
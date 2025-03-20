package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.samzuhalsetiawan.floss.domain.GetAllMusicError
import com.samzuhalsetiawan.floss.domain.Result
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.usecase.GetAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.PlayerUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MusicListScreenViewModel(
   private val getAllMusic: GetAllMusic,
   private val playerUseCases: PlayerUseCases
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
         is MusicListScreenEvent.OnPauseButtonClick -> onPauseButtonClick()
         is MusicListScreenEvent.OnPlayButtonClick -> onPlayButtonClick(event.music)
         is MusicListScreenEvent.OnNextButtonClick -> onNextButtonClick()
         is MusicListScreenEvent.OnPrevButtonClick -> onPrevButtonClick()
         is MusicListScreenEvent.OnRepeatButtonClick -> onRepeatButtonClick(event.repeatMode)
         is MusicListScreenEvent.OnShuffleButtonClick -> onShuffleButtonClick(event.isActive)
      }
   }

   init {
      getMusics()
      viewModelScope.launch {
         registerPlayerListener()
      }
   }

   suspend fun registerPlayerListener() = supervisorScope {
      launch {
         playerUseCases.getIsPlayingFlow().collect {
            _state.update { currentState ->
               currentState.copy(isPlaying = it)
            }
         }
      }
      launch {
         playerUseCases.getCurrentMusicFlow().collect {
            _state.update { currentState ->
               currentState.copy(currentMusic = it)
            }
         }
      }
      launch {
         playerUseCases.getRepeatModeFlow().collect {
            _state.update { currentState ->
               currentState.copy(repeatMode = it)
            }
         }
      }
      launch {
         playerUseCases.getShuffleModeEnabledFlow().collect {
            _state.update { currentState ->
               currentState.copy(isShuffleModeActive = it)
            }
         }
      }
   }

   private fun onShuffleButtonClick(isActive: Boolean) {
      playerUseCases.setShuffleModeEnabled(isActive)
   }

   private fun onRepeatButtonClick(repeatMode: RepeatMode) {
      playerUseCases.setRepeatMode(repeatMode)
   }

   private fun onPrevButtonClick() {
      playerUseCases.playPreviousMusic()
   }

   private fun onNextButtonClick() {
      playerUseCases.playNextMusic()
   }

   private fun onPauseButtonClick() {
      playerUseCases.pauseMusic()
   }

   private fun onPlayButtonClick(music: Music) {
      playerUseCases.playMusic(music)
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
      getMusics()
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

   private fun getMusics() {
      viewModelScope.launch(Dispatchers.IO) {
         _state.update { it.copy(isLoading = true) }
         val result = getAllMusic()
         when (result) {
            is Result.Success -> {
               _state.update {
                  it.copy(isLoading = false, musics = result.data)
               }
            }
            is Result.Failed -> {
               when (result.error) {
                  GetAllMusicError.NOT_ALLOWED_TO_READ_USER_MEDIA_AUDIO -> TODO("Request Permission")
               }
            }
         }
      }
   }

}
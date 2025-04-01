package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.GetAllMusicError
import com.samzuhalsetiawan.floss.domain.Result
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.domain.usecase.GetAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.PlayerUseCases
import com.samzuhalsetiawan.floss.presentation.common.model.Music
import com.samzuhalsetiawan.floss.presentation.common.util.toMusic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

private typealias RawMusic = com.samzuhalsetiawan.floss.domain.model.Music

class MusicListScreenViewModel(
   private val getAllMusic: GetAllMusic,
   private val playerUseCases: PlayerUseCases
): ViewModel() {

   private val _rawMusics = MutableStateFlow<List<RawMusic>>(emptyList())

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
         is MusicListScreenEvent.OnResumeButtonClick -> onResumeButtonClick()
         is MusicListScreenEvent.ShowBottomFloatingMusicListItem -> onShowBottomFloatingMusicListItem()
         is MusicListScreenEvent.ShowTopFloatingMusicListItem -> onShowTopFloatingMusicListItem()
         is MusicListScreenEvent.HideFloatingMusicListItem -> onHideFloatingMusicListItem()
      }
   }

   init {
      viewModelScope.launch {
         _state.update { it.copy(isLoading = true) }
         getMusics()
         registerPlayerListener()
         _state.update { it.copy(isLoading = false) }
      }
      viewModelScope.launch {
         _rawMusics.collect {
            _state.update { currentState ->
               currentState.copy(
                  musics = it.map { rawMusic -> rawMusic.toMusic() }
               )
            }
         }
      }
   }

   suspend fun registerPlayerListener() = CoroutineScope(coroutineContext).launch {
      launch {
         playerUseCases.getIsPlayingFlow().collect {
            _state.update { currentState ->
               currentState.copy(isPlaying = it)
            }
         }
      }
      launch {
         playerUseCases.getCurrentMusicIdFlow().collect { musicId ->
            val music = _state.value.musics.find { it.id == musicId }
            _state.update { currentState ->
               currentState.copy(currentMusic = music)
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

   private fun onHideFloatingMusicListItem() {
      _state.update { currentState ->
         currentState.copy(
            showTopFloatingMusicListItem = false,
            showBottomFloatingMusicListItem = false
         )
      }
   }

   private fun onShowTopFloatingMusicListItem() {
      _state.update { currentState ->
         currentState.copy(showTopFloatingMusicListItem = true)
      }
   }

   private fun onShowBottomFloatingMusicListItem() {
      _state.update { currentState ->
         currentState.copy(showBottomFloatingMusicListItem = true)
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

   private fun onResumeButtonClick() {
      playerUseCases.resumeMusic()
   }

   private fun onPlayButtonClick(music: Music) {
      val playlist = _rawMusics.value
      val musicIndex = playlist.indexOfFirst { it.id == music.id }
      playerUseCases.playPlaylist(playlist, musicIndex)
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
      viewModelScope.launch {
         _state.update { it.copy(isLoading = true) }
         getMusics()
         _state.update { it.copy(isLoading = false) }
      }
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

   private suspend fun getMusics() {
      withContext(Dispatchers.IO) {
         val result = getAllMusic()
         when (result) {
            is Result.Success -> {
               _rawMusics.update { result.data }
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
package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.GetAllMusicError
import com.samzuhalsetiawan.floss.domain.Result
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.domain.usecase.GetAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.ListenToPlayerEvent
import com.samzuhalsetiawan.floss.domain.usecase.PauseMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayNextMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayPreviousMusic
import com.samzuhalsetiawan.floss.domain.usecase.ReloadMusics
import com.samzuhalsetiawan.floss.domain.usecase.ResumeMusic
import com.samzuhalsetiawan.floss.domain.usecase.SetRepeatMode
import com.samzuhalsetiawan.floss.domain.usecase.SetShuffleModeEnabled
import com.samzuhalsetiawan.floss.presentation.common.model.Music
import com.samzuhalsetiawan.floss.presentation.common.util.toMusic
import com.samzuhalsetiawan.floss.presentation.common.util.updateByCollectFromFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicListScreenViewModel(
   private val getAllMusic: GetAllMusic,
   private val reloadMusics: ReloadMusics,
   private val playAllMusic: PlayAllMusic,
   private val playNextMusic: PlayNextMusic,
   private val playPreviousMusic: PlayPreviousMusic,
   private val pauseMusic: PauseMusic,
   private val resumeMusic: ResumeMusic,
   private val setShuffleModeEnabled: SetShuffleModeEnabled,
   private val setRepeatMode: SetRepeatMode,
   private val listenToPlayerEvent: ListenToPlayerEvent,
): ViewModel(), PlayerManager.Listener {

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

   private fun showLoading() {
      _state.update { it.copy(isLoading = true) }
   }

   private fun hideLoading() {
      _state.update { it.copy(isLoading = false) }
   }

   init {
      getMusics()
      listenToPlayerEvent(viewModelScope, this)
   }

   override fun onIsPlayingChanged(isPlaying: Boolean) {
      _state.update { currentState ->
         currentState.copy(isPlaying = isPlaying)
      }
   }

   override fun onCurrentMusicChanged(currentMusicId: String?) {
      val music = _state.value.musics.find { it.id == currentMusicId }
      _state.update { currentState ->
         currentState.copy(currentMusic = music)
      }
   }

   override fun onRepeatModeChanged(repeatMode: RepeatMode) {
      _state.update { currentState ->
         currentState.copy(repeatMode = repeatMode)
      }
   }

   override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
      _state.update { currentState ->
         currentState.copy(isShuffleModeActive = shuffleModeEnabled)
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
      setShuffleModeEnabled(isActive)
   }

   private fun onRepeatButtonClick(repeatMode: RepeatMode) {
      setRepeatMode(repeatMode)
   }

   private fun onPrevButtonClick() {
      playPreviousMusic()
   }

   private fun onNextButtonClick() {
      playNextMusic()
   }

   private fun onPauseButtonClick() {
      pauseMusic()
   }

   private fun onResumeButtonClick() {
      resumeMusic()
   }

   private fun onPlayButtonClick(music: Music) {
      playAllMusic(startingMusicId = music.id)
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
         showLoading()
         reloadMusics()
         hideLoading()
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

   private fun getMusics() {
      showLoading()
      when (val result = getAllMusic()) {
         is Result.Success -> {
            _state.updateByCollectFromFlow(viewModelScope, result.data) { currentState, musics ->
               currentState.copy(musics = musics.map { it.toMusic() }).also { hideLoading() }
            }
         }
         is Result.Failed -> {
            hideLoading()
            when (result.error) {
               GetAllMusicError.NOT_ALLOWED_TO_READ_USER_MEDIA_AUDIO -> TODO("Request Permission")
            }
         }
      }
   }

}
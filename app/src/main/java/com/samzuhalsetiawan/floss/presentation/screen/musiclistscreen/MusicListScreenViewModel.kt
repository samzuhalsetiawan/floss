package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.manager.Permission
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.domain.manager.onDenied
import com.samzuhalsetiawan.floss.domain.manager.onDeniedAndDoNotAskAgain
import com.samzuhalsetiawan.floss.domain.manager.onGranted
import com.samzuhalsetiawan.floss.domain.usecase.CheckIfPermissionGranted
import com.samzuhalsetiawan.floss.domain.usecase.GetMusics
import com.samzuhalsetiawan.floss.domain.usecase.ListenToPlayerEvent
import com.samzuhalsetiawan.floss.domain.usecase.PauseMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayNextMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayPreviousMusic
import com.samzuhalsetiawan.floss.domain.usecase.ReloadMusics
import com.samzuhalsetiawan.floss.domain.usecase.RequestReadAudioFilesPermission
import com.samzuhalsetiawan.floss.domain.usecase.ResumeMusic
import com.samzuhalsetiawan.floss.domain.usecase.SetRepeatMode
import com.samzuhalsetiawan.floss.domain.usecase.SetShuffleModeEnabled
import com.samzuhalsetiawan.floss.presentation.common.model.Music
import com.samzuhalsetiawan.floss.presentation.common.util.toMusic
import com.samzuhalsetiawan.floss.presentation.common.util.updateOnOtherFlowEmission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicListScreenViewModel(
   private val getMusics: GetMusics,
   private val reloadMusics: ReloadMusics,
   private val playAllMusic: PlayAllMusic,
   private val playNextMusic: PlayNextMusic,
   private val playPreviousMusic: PlayPreviousMusic,
   private val pauseMusic: PauseMusic,
   private val resumeMusic: ResumeMusic,
   private val setShuffleModeEnabled: SetShuffleModeEnabled,
   private val setRepeatMode: SetRepeatMode,
   private val requestReadAudioFilesPermission: RequestReadAudioFilesPermission,
   private val listenToPlayerEvent: ListenToPlayerEvent,
   private val checkIfPermissionGranted: CheckIfPermissionGranted,
): ViewModel(), PlayerManager.Listener {

   private val _state = MutableStateFlow(MusicListScreenState())
   val state = _state.asStateFlow()

   init {
      viewModelScope.launch {
         showLoading {
            checkForRequiredPermission()
            subscribeToUserMusicsFlow()
            launch { subscribeToMusicPlayerEvents() }
         }
      }
   }

   fun onEvent(event: MusicListScreenEvent) {
      when (event) {
         is MusicListScreenEvent.OnMissingPermissionBarGrantPermissionButtonClick -> onMissingPermissionBarGrantPermissionButtonClick()
         is MusicListScreenEvent.OnMissingPermissionBarDismissButtonClick -> onMissingPermissionBarDismissButtonClick()
         is MusicListScreenEvent.OnPermissionDeniedPermanentlyAlertDialogOpenSettingsButtonClick -> onPermissionDeniedPermanentlyAlertDialogOpenSettingsButtonClick()
         is MusicListScreenEvent.OnPermissionDeniedPermanentlyAlertDialogIgnoreButtonClick -> onPermissionDeniedPermanentlyAlertDialogIgnoreButtonClick()
         is MusicListScreenEvent.OnPauseButtonClick -> onPauseButtonClick()
         is MusicListScreenEvent.OnMusicListItemPlayButtonClick -> onMusicListItemPlayButtonClick(event.music)
         is MusicListScreenEvent.OnNextButtonClick -> onNextButtonClick()
         is MusicListScreenEvent.OnPrevButtonClick -> onPrevButtonClick()
         is MusicListScreenEvent.OnRepeatButtonClick -> onRepeatButtonClick(event.repeatMode)
         is MusicListScreenEvent.OnShuffleButtonClick -> onShuffleButtonClick(event.isActive)
      }
   }

   private fun checkForRequiredPermission() {
      if (!checkIfPermissionGranted(Permission.READ_USER_MEDIA_AUDIO)) {
         showMissingPermissionBar()
      }
   }

   private suspend fun subscribeToUserMusicsFlow() {
      getMusics().apply {
         _state.update { it.copy(musics = first().map { music -> music.toMusic() }) }
         _state.updateOnOtherFlowEmission(this) { currentState, value ->
            currentState.copy(musics = value.map { it.toMusic() })
         }
      }
   }

   private suspend fun subscribeToMusicPlayerEvents() {
      listenToPlayerEvent(this@MusicListScreenViewModel)
   }

   private fun onMissingPermissionBarGrantPermissionButtonClick() {
      viewModelScope.launch {
         requestReadAudioFilesPermission()
            .onGranted { refreshMusicList().also { hideMissingPermissionBar() } }
            .onDenied { showMissingPermissionBar() }
            .onDeniedAndDoNotAskAgain { showAlertDialog(AlertDialog.ReadAudioFilesPermissionDeniedPermanently) }
      }
   }

   private fun onMissingPermissionBarDismissButtonClick() {
      hideMissingPermissionBar()
   }

   private fun onPermissionDeniedPermanentlyAlertDialogOpenSettingsButtonClick() {
      TODO("Not yet implemented")
   }

   private fun onPermissionDeniedPermanentlyAlertDialogIgnoreButtonClick() {
      hideAlertDialog(AlertDialog.ReadAudioFilesPermissionDeniedPermanently)
   }

   private fun onPauseButtonClick() {
      pauseMusic()
   }

   private fun onMusicListItemPlayButtonClick(music: Music?) {
      if (music == null) return
      if (music.id != _state.value.currentMusic?.id) {
         playAllMusic(startingMusicId = music.id)
      } else {
         if (!state.value.isPlaying) {
            resumeMusic()
         }
      }
   }

   private fun onNextButtonClick() {
      playNextMusic()
   }

   private fun onPrevButtonClick() {
      playPreviousMusic()
   }

   private fun onRepeatButtonClick(repeatMode: RepeatMode) {
      setRepeatMode(repeatMode)
   }

   private fun onShuffleButtonClick(isActive: Boolean) {
      setShuffleModeEnabled(isActive)
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

   private suspend fun refreshMusicList() {
      showLoading {
         reloadMusics()
      }
   }

   private inline fun <T> showLoading(action: () -> T): T {
      _state.update { currentState ->
         currentState.copy(isLoading = true)
      }
      return action().also {
         _state.update { currentState ->
            currentState.copy(isLoading = false)
         }
      }
   }

   private fun hideMissingPermissionBar() {
      _state.update { currentState ->
         currentState.copy(
            isMissingPermissionBarShowed = false
         )
      }
   }

   private fun showMissingPermissionBar() {
      _state.update { currentState ->
         currentState.copy(isMissingPermissionBarShowed = true)
      }
   }

   private fun hideAlertDialog(alertDialog: AlertDialog) {
      _state.update { currentState ->
         currentState.copy(alertDialogs = currentState.alertDialogs - alertDialog)
      }
   }

   private fun showAlertDialog(alertDialog: AlertDialog) {
      _state.update { currentState ->
         currentState.copy(alertDialogs = currentState.alertDialogs + alertDialog)
      }
   }

}
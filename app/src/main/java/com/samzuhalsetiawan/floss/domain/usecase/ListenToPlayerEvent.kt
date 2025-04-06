package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ListenToPlayerEvent(
   private val playerManager: PlayerManager
) {
   suspend operator fun invoke(listener: PlayerManager.Listener) {
      supervisorScope {
         launch {
            playerManager.isPlaying.collect {
               listener.onIsPlayingChanged(it)
            }
         }
         launch {
            playerManager.currentMusicId.collect {
               listener.onCurrentMusicChanged(it)
            }
         }
         launch {
            playerManager.repeatMode.collect {
               listener.onRepeatModeChanged(it)
            }
         }
         launch {
            playerManager.shuffleEnabled.collect {
               listener.onShuffleModeEnabledChanged(it)
            }
         }
      }
   }
}
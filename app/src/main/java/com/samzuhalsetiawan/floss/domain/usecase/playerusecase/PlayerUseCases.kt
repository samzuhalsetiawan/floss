package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class PlayerUseCases(
   private val playerManager: PlayerManager
) {
   val getCurrentMusicFlow = GetCurrentMusicFlow(playerManager)
   val getIsPlayingFlow = GetIsPlayingFlow(playerManager)
   val getRepeatModeFlow = GetRepeatModeFlow(playerManager)
   val getShuffleModeEnabledFlow = GetShuffleModeEnabledFlow(playerManager)
   val playMusic = PlayMusic(playerManager)
   val playNextMusic = PlayNextMusic(playerManager)
   val playPreviousMusic = PlayPreviousMusic(playerManager)
   val setRepeatMode = SetRepeatMode(playerManager)
   val setShuffleModeEnabled = SetShuffleModeEnabled(playerManager)
   val pauseMusic = PauseMusic(playerManager)
}
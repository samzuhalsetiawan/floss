package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class GetCurrentPlayerState(
   private val playerManager: PlayerManager
) {
   operator fun invoke(): PlayerManager.PlayerState {
      return PlayerManager.PlayerState(
         isPlaying = playerManager.isPlaying,
         shuffleEnabled = playerManager.shuffleEnabled,
         currentMusic = playerManager.currentMusic,
         repeatMode = playerManager.repeatMode
      )
   }
}
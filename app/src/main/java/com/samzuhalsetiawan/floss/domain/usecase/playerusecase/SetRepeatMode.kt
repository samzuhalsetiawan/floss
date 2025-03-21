package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class SetRepeatMode(
   private val playerManager: PlayerManager
) {
   operator fun invoke(repeatMode: PlayerManager.RepeatMode) {
      playerManager.setRepeatMode(repeatMode)
   }
}
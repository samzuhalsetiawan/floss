package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import kotlinx.coroutines.flow.StateFlow

class GetRepeatModeFlow(
   private val playerManager: PlayerManager
) {
   operator fun invoke(): StateFlow<PlayerManager.RepeatMode> {
      return playerManager.repeatMode
   }
}
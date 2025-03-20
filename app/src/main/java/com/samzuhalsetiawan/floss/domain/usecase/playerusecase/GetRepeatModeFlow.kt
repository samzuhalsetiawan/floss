package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import kotlinx.coroutines.flow.SharedFlow

class GetRepeatModeFlow(
   private val playerManager: PlayerManager
) {
   operator fun invoke(): SharedFlow<PlayerManager.RepeatMode> {
      return playerManager.repeatModeFlow
   }
}
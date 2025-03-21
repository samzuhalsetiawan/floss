package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class GetShuffleModeEnabledFlow(
   private val playerManager: PlayerManager
) {
   operator fun invoke(): StateFlow<Boolean> {
      return playerManager.shuffleEnabled
   }
}
package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import kotlinx.coroutines.flow.SharedFlow

class GetShuffleModeEnabledFlow(
   private val playerManager: PlayerManager
) {
   operator fun invoke(): SharedFlow<Boolean> {
      return playerManager.shuffleEnabledFlow
   }
}
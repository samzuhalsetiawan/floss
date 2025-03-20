package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.SharedFlow

class GetCurrentMusicFlow(
   private val playerManager: PlayerManager
) {
   operator fun invoke(): SharedFlow<Music?> {
      return playerManager.currentMusicFlow
   }
}
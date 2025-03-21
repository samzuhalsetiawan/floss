package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.StateFlow

class GetCurrentMusicFlow(
   private val playerManager: PlayerManager
) {
   operator fun invoke(): StateFlow<Music?> {
      return playerManager.currentMusic
   }
}
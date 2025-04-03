package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class PauseMusic(
   private val playerManager: PlayerManager
) {
   operator fun invoke() {
      playerManager.pause()
   }
}
package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class PlayPreviousMusic(
   private val playerManager: PlayerManager
) {
   operator fun invoke() {
      playerManager.previous()
   }
}
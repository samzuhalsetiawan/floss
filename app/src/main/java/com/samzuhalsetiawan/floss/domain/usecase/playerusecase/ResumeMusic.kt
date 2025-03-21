package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class ResumeMusic(
   private val playerManager: PlayerManager
) {
   operator fun invoke() {
      playerManager.resume()
   }
}
package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class ReleasePlayerResources(
   private val playerManager: PlayerManager
) {
   operator fun invoke() {
      playerManager.destroy()
   }
}
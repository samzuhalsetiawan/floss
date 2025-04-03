package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class SetShuffleModeEnabled(
   private val playerManager: PlayerManager
) {
   operator fun invoke(shuffleEnabled: Boolean) {
      playerManager.setShuffleEnabled(shuffleEnabled)
   }
}
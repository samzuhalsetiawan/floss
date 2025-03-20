package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.model.Music

class PlayMusic(
   private val playerManager: PlayerManager
) {
   operator fun invoke(music: Music) {
      playerManager.play(music)
   }
}
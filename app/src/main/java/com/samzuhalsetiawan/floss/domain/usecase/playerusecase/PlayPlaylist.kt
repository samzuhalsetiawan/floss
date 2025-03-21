package com.samzuhalsetiawan.floss.domain.usecase.playerusecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.model.Music

class PlayPlaylist(
   private val playerManager: PlayerManager
) {
   operator fun invoke(musics: List<Music>, startPosition: Int) {
      playerManager.play(musics, startPosition)
   }
}
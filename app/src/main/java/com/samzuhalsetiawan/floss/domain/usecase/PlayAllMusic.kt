package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PlayAllMusic(
   private val playerManager: PlayerManager,
   musicRepository: MusicRepository,
   applicationScope: CoroutineScope
) {

   private val musics = musicRepository.musics
      .stateIn(applicationScope, SharingStarted.Eagerly, emptyList())

   operator fun invoke(startingMusicId: String? = null) {
      val musics = musics.value.takeIf { it.isNotEmpty() } ?: return
      val startPosition = startingMusicId?.let {
         musics.indexOfFirst { it.id == startingMusicId }
      } ?: 0
      playerManager.play(musics, startPosition)
   }
}
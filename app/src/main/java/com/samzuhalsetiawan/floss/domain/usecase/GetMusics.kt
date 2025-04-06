package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetMusics(
   private val musicRepository: MusicRepository,
) {
   operator fun invoke(): Flow<List<Music>> {
      return musicRepository.musics
   }
}
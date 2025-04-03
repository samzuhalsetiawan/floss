package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.repository.MusicRepository

class ReloadMusics(
   private val musicRepository: MusicRepository
) {
   suspend operator fun invoke() {
      musicRepository.reloadMusics()
   }
}
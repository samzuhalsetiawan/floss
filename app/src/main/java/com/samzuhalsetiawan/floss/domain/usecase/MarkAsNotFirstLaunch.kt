package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.repository.PreferencesRepository

class MarkAsNotFirstLaunch(
   private val preferencesRepository: PreferencesRepository
) {
   suspend operator fun invoke() {
      preferencesRepository.setIsFirstLaunch(false)
   }
}
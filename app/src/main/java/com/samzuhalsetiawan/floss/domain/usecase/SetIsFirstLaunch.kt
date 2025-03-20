package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.repository.PreferencesRepository

class SetIsFirstLaunch(
   private val preferencesRepository: PreferencesRepository
) {
   suspend operator fun invoke(isFirstLaunch: Boolean) {
      preferencesRepository.setIsFirstLaunch(isFirstLaunch)
   }
}
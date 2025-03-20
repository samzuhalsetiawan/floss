package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.repository.PreferencesRepository

class GetIsFirstLaunch(
   private val preferencesRepository: PreferencesRepository
) {
   suspend operator fun invoke(): Boolean {
      return preferencesRepository.isFirstLaunch()
   }
}
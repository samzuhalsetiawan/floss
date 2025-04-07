package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.Destination
import com.samzuhalsetiawan.floss.domain.repository.PreferencesRepository

class DecideStartDestination(
   private val preferencesRepository: PreferencesRepository
) {
   suspend operator fun invoke(): Destination.Graph {
      val isFirstLaunch = preferencesRepository.isFirstLaunch()
      return if (isFirstLaunch) Destination.Graph.Welcome else Destination.Graph.Main
   }
}
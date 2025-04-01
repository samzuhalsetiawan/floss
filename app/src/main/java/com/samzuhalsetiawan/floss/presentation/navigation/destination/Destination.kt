package com.samzuhalsetiawan.floss.presentation.navigation.destination

import kotlinx.serialization.Serializable

sealed interface Destination {

   sealed class Screen : Destination {

      @Serializable
      data object OnboardingScreen : Screen()

      @Serializable
      data object PermissionRequestScreen : Screen()

      @Serializable
      data object ControlScreen : Screen()

      @Serializable
      data object MusicListScreen : Screen()
   }

   sealed class Graph : Destination {

      @Serializable
      data object Welcome : Graph()

      @Serializable
      data object Main : Graph()
   }
}
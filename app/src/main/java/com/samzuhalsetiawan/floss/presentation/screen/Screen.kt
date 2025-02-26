package com.samzuhalsetiawan.floss.presentation.screen

import kotlinx.serialization.Serializable

sealed class Screen {
   @Serializable
   data object ControlScreen : Screen()
   @Serializable
   data object MusicListScreen : Screen()
}
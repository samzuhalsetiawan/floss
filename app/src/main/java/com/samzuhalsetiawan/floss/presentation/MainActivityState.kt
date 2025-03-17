package com.samzuhalsetiawan.floss.presentation

import androidx.media3.session.MediaController
import com.samzuhalsetiawan.floss.presentation.navigation.Destination

data class MainActivityState(
   val isAppReady: Boolean = false,
   val startDestination: Destination.Graph? = null,
   val mediaController: MediaController? = null
)

sealed interface MainActivityEvent {
   data class SetMediaController(val mediaController: MediaController) : MainActivityEvent
}
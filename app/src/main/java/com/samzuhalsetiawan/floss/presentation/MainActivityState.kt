package com.samzuhalsetiawan.floss.presentation

import com.samzuhalsetiawan.floss.presentation.navigation.Destination

data class MainActivityState(
   val isAppReady: Boolean = false,
   val startDestination: Destination.Graph? = null,
)
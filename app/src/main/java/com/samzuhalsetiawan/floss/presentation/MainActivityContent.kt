package com.samzuhalsetiawan.floss.presentation

import androidx.activity.compose.setContent
import com.samzuhalsetiawan.floss.presentation.navigation.MainNavigation
import com.samzuhalsetiawan.floss.presentation.navigation.destination.Destination
import com.samzuhalsetiawan.floss.presentation.navigation.destination.main.mainGraph
import com.samzuhalsetiawan.floss.presentation.navigation.destination.main.musiclistscreen.musicListScreen
import com.samzuhalsetiawan.floss.presentation.navigation.destination.welcome.onboardingscreen.onboardingScreen
import com.samzuhalsetiawan.floss.presentation.navigation.destination.welcome.permissionrequestscreen.permissionRequestScreen
import com.samzuhalsetiawan.floss.presentation.navigation.destination.welcome.welcomeGraph
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

fun MainActivity.setMainContent(
   startDestination: Destination.Graph
) {
   setContent {
      FlossTheme {
         MainNavigation(startDestination) {
            welcomeGraph {
               onboardingScreen()
               permissionRequestScreen()
            }
            mainGraph {
               musicListScreen()
            }
         }
      }
   }
}
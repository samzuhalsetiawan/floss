package com.samzuhalsetiawan.floss.presentation.navigation.destination.welcome.onboardingscreen

import androidx.navigation.compose.composable
import com.samzuhalsetiawan.floss.presentation.navigation.MainNavigationScope
import com.samzuhalsetiawan.floss.presentation.navigation.destination.Destination
import com.samzuhalsetiawan.floss.presentation.screen.onboardingscreen.OnboardingScreen
import com.samzuhalsetiawan.floss.presentation.screen.onboardingscreen.OnboardingScreenNavigationEvent

fun MainNavigationScope.onboardingScreen() {
   with(navGraphBuilder) {
      composable<Destination.Screen.OnboardingScreen> {
         OnboardingScreen(
            onNavigationEvent = { navigationEvent ->
               when (navigationEvent) {
                  is OnboardingScreenNavigationEvent.NavigateToPermissionRequestScreen -> {
                     navController.navigate(Destination.Screen.PermissionRequestScreen)
                  }
               }
            }
         )
      }
   }
}
package com.samzuhalsetiawan.floss.presentation.navigation.destination.welcome

import androidx.navigation.compose.navigation
import com.samzuhalsetiawan.floss.presentation.navigation.MainNavigationScope
import com.samzuhalsetiawan.floss.domain.Destination

fun MainNavigationScope.welcomeGraph(
   builder: MainNavigationScope.() -> Unit
) {
   with(navGraphBuilder) {
      navigation<Destination.Graph.Welcome>(
         startDestination = Destination.Screen.OnboardingScreen
      ) {
         builder(
            MainNavigationScope(
               navGraphBuilder = this,
               navController = navController
            )
         )
      }
   }
}
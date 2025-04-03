package com.samzuhalsetiawan.floss.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.floss.domain.Destination

@Composable
fun MainNavigation(
   startDestination: Destination.Graph,
   navController: NavHostController = rememberNavController(),
   graph: MainNavigationScope.() -> Unit
) {
   NavHost(
      navController = navController,
      startDestination = startDestination,
   ) {
      graph(
         MainNavigationScope(
            navGraphBuilder = this@NavHost,
            navController = navController
         )
      )
   }
}

class MainNavigationScope(
   val navGraphBuilder: NavGraphBuilder,
   val navController: NavHostController
)
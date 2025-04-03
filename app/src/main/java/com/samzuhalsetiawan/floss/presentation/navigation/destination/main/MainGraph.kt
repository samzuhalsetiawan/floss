package com.samzuhalsetiawan.floss.presentation.navigation.destination.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.floss.presentation.navigation.MainNavigationScope
import com.samzuhalsetiawan.floss.presentation.navigation.component.mainbottomnavigation.MainBottomNavigation
import com.samzuhalsetiawan.floss.domain.Destination

fun MainNavigationScope.mainGraph(
   builder: MainNavigationScope.() -> Unit
) {
   with(navGraphBuilder) {
      composable<Destination.Graph.Main> {
         val navController = rememberNavController()
         Scaffold(
            bottomBar = { MainBottomNavigation(navController) }
         ) { paddingValues ->
            NavHost(
               modifier = Modifier.padding(paddingValues),
               startDestination = Destination.Screen.MusicListScreen,
               navController = navController,
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
   }
}
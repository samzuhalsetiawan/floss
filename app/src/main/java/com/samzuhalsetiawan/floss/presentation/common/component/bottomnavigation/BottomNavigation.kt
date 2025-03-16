package com.samzuhalsetiawan.floss.presentation.common.component.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.floss.presentation.navigation.Destination

@Composable
fun BottomNavigation(
   navController: NavHostController = rememberNavController()
) {
   val navBackStackEntry by navController.currentBackStackEntryAsState()
   val currentDestination = navBackStackEntry?.destination

   NavigationBar {
      NavigationBarItem(
         selected = currentDestination?.hierarchy?.any { it.hasRoute<Destination.Screen.MusicListScreen>() } == true,
         onClick = {
            navController.navigate(Destination.Screen.MusicListScreen) {
               popUpTo(navController.graph.findStartDestination().id) {
                  saveState = true
               }
               launchSingleTop = true
               restoreState = true
            }
         },
         icon = {
            Icon(
               imageVector = Icons.AutoMirrored.Filled.List,
               contentDescription = null
            )
         },
         label = {
            Text("Music List")
         }
      )
      NavigationBarItem(
         selected = currentDestination?.hierarchy?.any { it.hasRoute<Destination.Screen.ControlScreen>() } == true,
         onClick = {
            navController.navigate(Destination.Screen.ControlScreen) {
               popUpTo(navController.graph.findStartDestination().id) {
                  saveState = true
               }
               launchSingleTop = true
               restoreState = true
            }
         },
         icon = {
            Icon(
               imageVector = Icons.Default.MusicNote,
               contentDescription = null
            )
         },
         label = {
            Text("Control")
         }
      )
   }
}
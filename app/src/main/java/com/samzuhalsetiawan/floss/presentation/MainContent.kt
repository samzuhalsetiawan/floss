package com.samzuhalsetiawan.floss.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.session.MediaController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.floss.domain.FlossApp
import com.samzuhalsetiawan.floss.presentation.common.component.bottomnavigation.BottomNavigation
import com.samzuhalsetiawan.floss.presentation.navigation.Destination
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreen
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreenViewModel
import com.samzuhalsetiawan.floss.presentation.screen.onboardingscreen.OnboardingScreen
import com.samzuhalsetiawan.floss.presentation.screen.onboardingscreen.OnboardingScreenNavigationEvent
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.PermissionRequestScreenNavigationEvent
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.PermissionRequestScreen
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.PermissionRequestScreenViewModel
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

fun MainActivity.setMainContent() {
   setContent {
      val viewModel = viewModel<MainViewModel>(this) {
         MainViewModel(
            preferences = (application as FlossApp).modules.preferences
         )
      }
      val state by viewModel.state.collectAsStateWithLifecycle()
      MainContent(
         state = state,
         onEvent = viewModel::onEvent
      )
   }
}

@Composable
private fun MainActivity.MainContent(
   state: MainActivityState,
   onEvent: (MainActivityEvent) -> Unit,
) {
   val navController = rememberNavController()
   val currentBackStackEntry by navController.currentBackStackEntryAsState()
   val startDestination = state.startDestination ?: return
   val mediaController = state.mediaController ?: return
   FlossTheme {
      Scaffold(
         bottomBar = {
            val shouldShowBottomNavigation =
               currentBackStackEntry?.destination?.hasRoute(Destination.Screen.MusicListScreen::class) == true ||
                     currentBackStackEntry?.destination?.hasRoute(Destination.Screen.ControlScreen::class) == true
            if (shouldShowBottomNavigation) {
               BottomNavigation(navController)
            }
         }
      ) { scaffoldPadding ->
         NavHost(
            modifier = Modifier.padding(scaffoldPadding),
            navController = navController,
            startDestination = startDestination,
         ) {
            navigation<Destination.Graph.Welcome>(
               startDestination = Destination.Screen.OnboardingScreen,
            ) {
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
               composable<Destination.Screen.PermissionRequestScreen> {
                  val vm = viewModel<PermissionRequestScreenViewModel> {
                     PermissionRequestScreenViewModel(
                        preferences = (application as FlossApp).modules.preferences
                     )
                  }
                  PermissionRequestScreen(
                     viewModel = vm,
                     onNavigationEvent = { navigationEvent ->
                        when (navigationEvent) {
                           is PermissionRequestScreenNavigationEvent.NavigateToMusicListScreen -> {
                              navController.navigate(Destination.Graph.Main) {
                                 popUpTo(Destination.Graph.Welcome) {
                                    inclusive = true
                                 }
                              }
                           }
                        }
                     },
                  )
               }
            }
            navigation<Destination.Graph.Main>(
               startDestination = Destination.Screen.MusicListScreen,
            ) {
               composable<Destination.Screen.MusicListScreen> {
                  val vm = viewModel<MusicListScreenViewModel> {
                     MusicListScreenViewModel(
                        musicRepository = (application as FlossApp).modules.musicRepository,
                        mediaController = mediaController
                     )
                  }
                  MusicListScreen(vm)
               }
            }
         }
      }
   }
}
package com.samzuhalsetiawan.floss.presentation.navigation.destination.welcome.permissionrequestscreen

import androidx.navigation.compose.composable
import com.samzuhalsetiawan.floss.presentation.navigation.MainNavigationScope
import com.samzuhalsetiawan.floss.presentation.navigation.destination.Destination
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.PermissionRequestScreen
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.PermissionRequestScreenNavigationEvent
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.PermissionRequestScreenViewModel
import org.koin.androidx.compose.koinViewModel

fun MainNavigationScope.permissionRequestScreen() {
   with(navGraphBuilder) {
      composable<Destination.Screen.PermissionRequestScreen> {
         val vm: PermissionRequestScreenViewModel = koinViewModel()
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
}
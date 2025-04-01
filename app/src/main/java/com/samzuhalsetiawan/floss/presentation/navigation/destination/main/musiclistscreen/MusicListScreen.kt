package com.samzuhalsetiawan.floss.presentation.navigation.destination.main.musiclistscreen

import androidx.navigation.compose.composable
import com.samzuhalsetiawan.floss.presentation.navigation.MainNavigationScope
import com.samzuhalsetiawan.floss.presentation.navigation.destination.Destination
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreen
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreenViewModel
import org.koin.androidx.compose.koinViewModel

fun MainNavigationScope.musicListScreen() {
   with(navGraphBuilder) {
      composable<Destination.Screen.MusicListScreen> {
         val vm: MusicListScreenViewModel = koinViewModel()
         MusicListScreen(vm)
      }
   }
}
package com.samzuhalsetiawan.floss.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.floss.domain.FlossApp
import com.samzuhalsetiawan.floss.presentation.screen.Screen
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.ControlScreen
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreen
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreenVM
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityVM>()

    private val requestReadAudioFilesPermissionLauncher = registerForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        callback = {

        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !viewModel.isAppReady }
        enableEdgeToEdge()
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          requestReadAudioFilesPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_AUDIO)
       }
       setContent {
            FlossTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.hasRoute<Screen.MusicListScreen>() } == true,
                                onClick = {
                                    navController.navigate(Screen.MusicListScreen) {
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
                                selected = currentDestination?.hierarchy?.any { it.hasRoute<Screen.ControlScreen>() } == true,
                                onClick = {
                                    navController.navigate(Screen.ControlScreen) {
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
                ) { scaffoldPadding ->
                    NavHost(
                        modifier = Modifier.padding(scaffoldPadding),
                        navController = navController,
                        startDestination = Screen.MusicListScreen
                    ) {
                        composable<Screen.ControlScreen> {
                            ControlScreen()
                        }
                        composable<Screen.MusicListScreen> {
                            val vm by viewModels<MusicListScreenVM> {
                                viewModelFactory {
                                    addInitializer(MusicListScreenVM::class) {
                                        MusicListScreenVM(musicRepository = (application as FlossApp).modules.musicRepository)
                                    }
                                }
                            }
                            MusicListScreen(vm)
                        }
                    }
                }
            }
        }
    }
}
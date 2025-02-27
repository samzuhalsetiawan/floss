package com.samzuhalsetiawan.floss.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                NavHost(
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
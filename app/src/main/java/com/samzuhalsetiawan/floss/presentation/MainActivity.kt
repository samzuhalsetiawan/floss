package com.samzuhalsetiawan.floss.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.floss.presentation.screen.Screen
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.ControlScreen
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlossTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.ControlScreen
                ) {
                    composable<Screen.ControlScreen> {
                        ControlScreen()
                    }
                }
            }
        }
    }
}
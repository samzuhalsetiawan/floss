package com.samzuhalsetiawan.floss.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

   private var isAppReady = false

   private val viewModel: MainActivityViewModel by viewModel()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val splashScreen = installSplashScreen()
      splashScreen.setKeepOnScreenCondition { !isAppReady }
      enableEdgeToEdge()
      viewModel.waitUntilInitializationCompleted {
         setMainContent(it)
         isAppReady = true
      }
   }

   override fun onDestroy() {
      viewModel.releaseResources()
      super.onDestroy()
   }
}

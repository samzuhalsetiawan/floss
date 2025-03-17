package com.samzuhalsetiawan.floss.presentation

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.samzuhalsetiawan.floss.domain.FlossApp
import com.samzuhalsetiawan.floss.domain.service.BackgroundPlayerService

class MainActivity : ComponentActivity() {

   private val viewModel by viewModels<MainViewModel>(
      factoryProducer = {
         viewModelFactory {
            addInitializer(clazz = MainViewModel::class) {
               MainViewModel(
                  preferences = (application as FlossApp).modules.preferences
               )
            }
         }
      }
   )

   private lateinit var controllerFuture: ListenableFuture<MediaController>

   override fun onStart() {
      super.onStart()
      val sessionToken = SessionToken(this, ComponentName(this, BackgroundPlayerService::class.java))
      controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
      controllerFuture.addListener({
         val mediaController = controllerFuture.get()
         viewModel.onEvent(MainActivityEvent.SetMediaController(mediaController))
      }, ContextCompat.getMainExecutor(this))
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val splashScreen = installSplashScreen()
      splashScreen.setKeepOnScreenCondition { !viewModel.state.value.isAppReady }
      enableEdgeToEdge()
      setMainContent()
   }

   override fun onStop() {
      MediaController.releaseFuture(controllerFuture)
      super.onStop()
   }
}

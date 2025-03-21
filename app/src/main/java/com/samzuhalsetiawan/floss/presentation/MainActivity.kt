package com.samzuhalsetiawan.floss.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samzuhalsetiawan.floss.FlossApp

class MainActivity : ComponentActivity() {

   private val viewModel by viewModels<MainActivityViewModel>(
      factoryProducer = {
         viewModelFactory {
            addInitializer(clazz = MainActivityViewModel::class) {
               MainActivityViewModel(
                  getIsFirstLaunch = (application as FlossApp).useCasesModule.getIsFirstLaunch,
                  releasePlayerResources = (application as FlossApp).useCasesModule.releasePlayerResources
               )
            }
         }
      }
   )

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val splashScreen = installSplashScreen()
      splashScreen.setKeepOnScreenCondition { !viewModel.state.value.isAppReady }
      enableEdgeToEdge()
      setMainContent()
   }

   override fun onDestroy() {
      viewModel.onEvent(MainActivityEvent.OnActivityDestroyed)
      super.onDestroy()
   }
}

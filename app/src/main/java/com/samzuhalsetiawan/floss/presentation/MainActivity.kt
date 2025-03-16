package com.samzuhalsetiawan.floss.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.floss.domain.FlossApp
import com.samzuhalsetiawan.floss.presentation.common.component.bottomnavigation.BottomNavigation
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val splashScreen = installSplashScreen()
      splashScreen.setKeepOnScreenCondition { !viewModel.isAppReady.value }
      enableEdgeToEdge()
      lifecycleScope.launch {
         val startDestination = viewModel.startDestination.first { it != null }
         withContext(Dispatchers.Main) { setMainContent(startDestination!!) }
      }
   }
}

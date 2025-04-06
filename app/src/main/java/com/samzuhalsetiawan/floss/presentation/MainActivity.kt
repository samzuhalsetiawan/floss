package com.samzuhalsetiawan.floss.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.samzuhalsetiawan.floss.domain.manager.Permission
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager
import com.samzuhalsetiawan.floss.domain.manager.PermissionResult
import com.samzuhalsetiawan.floss.presentation.common.config.Config
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
   ComponentActivity(),
   PermissionManager.Handler,
   ActivityResultCallback<Map<String, Boolean>> {

   private var isAppReady = false
   private val viewModel: MainActivityViewModel by viewModel()
   private val permissionManager: PermissionManager by inject()
   private val permissionRequestLauncher = registerForActivityResult(RequestMultiplePermissions(), this)

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      installSplashScreen().setKeepOnScreenCondition { !isAppReady }
      permissionManager.registerPermissionHandler(this)
      enableEdgeToEdge()
      viewModel.waitUntilInitializationCompleted {
         setMainContent(it)
         isAppReady = true
      }
   }

   override fun onHandlePermissionRequest(permission: Permission) {
      when (permission) {
         Permission.READ_USER_MEDIA_AUDIO -> {
            permissionRequestLauncher.launch(Config.readAudiFilesPermission)
         }
      }
   }

   override fun onActivityResult(result: Map<String, Boolean>) {
      // TODO: Need better solution
      val isGranted = result.values.all { it }
      val shouldShowRequestPermissionRationale = result.keys.any { shouldShowRequestPermissionRationale(it) }
      val permissionResult = when {
         isGranted -> PermissionResult.GRANTED
         shouldShowRequestPermissionRationale -> PermissionResult.DENIED
         else -> PermissionResult.DENIED_AND_DO_NOT_ASK_AGAIN
      }
      when {
         result.keys.containsAll(Config.readAudiFilesPermission.toList()) -> {
            permissionManager.onPermissionResult(Permission.READ_USER_MEDIA_AUDIO, permissionResult)
         }
      }
   }

   override fun onDestroy() {
      viewModel.releaseResources()
      permissionManager.unregisterPermissionHandler(this)
      super.onDestroy()
   }
}

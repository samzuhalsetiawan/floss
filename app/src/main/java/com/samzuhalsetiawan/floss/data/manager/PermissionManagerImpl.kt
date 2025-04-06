package com.samzuhalsetiawan.floss.data.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.samzuhalsetiawan.floss.domain.manager.Permission
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager
import com.samzuhalsetiawan.floss.domain.manager.PermissionResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.job
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class PermissionManagerImpl(
   private val applicationContext: Context
): PermissionManager {

   private var handler: PermissionManager.Handler? = null

   private var request: Pair<Permission, CompletableDeferred<PermissionResult>>? = null

   override fun registerPermissionHandler(handler: PermissionManager.Handler) {
      this.handler = handler
   }

   override fun unregisterPermissionHandler(handler: PermissionManager.Handler) {
      this.handler = null
   }

   @OptIn(ExperimentalCoroutinesApi::class)
   override suspend fun requestPermission(permission: Permission): PermissionResult {
      val job = coroutineContext.job
      return suspendCoroutine { continuation ->
         if (request != null) {
            request?.second?.cancel()
            request = null
         }
         CompletableDeferred<PermissionResult>(job)
            .also { request = permission to it }
            .apply {
               invokeOnCompletion {
                  continuation.resumeWith(Result.success(getCompleted()))
                  request = null
               }
            }
         handler?.onHandlePermissionRequest(permission)
      }
   }

   override fun onPermissionResult(permission: Permission, result: PermissionResult) {
      if (request?.first != permission) return
      request?.second?.complete(result)
   }

   override fun checkIfPermissionGranted(permission: Permission): Boolean {
      return when (permission) {
         Permission.READ_USER_MEDIA_AUDIO -> checkIfAllowedToReadUserMediaAudio()
      }
   }

   private fun checkIfAllowedToReadUserMediaAudio(): Boolean {
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
      } else {
         ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
      }
   }
}
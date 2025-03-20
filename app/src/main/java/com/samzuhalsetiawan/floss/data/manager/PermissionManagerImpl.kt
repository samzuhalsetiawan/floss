package com.samzuhalsetiawan.floss.data.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager

class PermissionManagerImpl(
   private val applicationContext: Context
): PermissionManager {
   override fun checkIfPermissionGranted(permission: PermissionManager.Permission): Boolean {
      return when (permission) {
         PermissionManager.Permission.READ_USER_MEDIA_AUDIO -> checkIfAllowedToReadUserMediaAudio()
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
package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.Permission
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager
import com.samzuhalsetiawan.floss.domain.manager.PermissionResult

class RequestReadAudioFilesPermission(
   private val permissionManager: PermissionManager
) {

   suspend operator fun invoke(): PermissionResult {
      return permissionManager.requestPermission(Permission.READ_USER_MEDIA_AUDIO)
   }

}
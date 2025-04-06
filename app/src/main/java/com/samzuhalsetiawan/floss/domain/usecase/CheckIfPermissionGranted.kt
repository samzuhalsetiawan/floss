package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.manager.Permission
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager

class CheckIfPermissionGranted(
   private val permissionManager: PermissionManager
) {

   operator fun invoke(permission: Permission): Boolean {
      return permissionManager.checkIfPermissionGranted(permission)
   }

}
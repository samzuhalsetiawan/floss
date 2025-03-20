package com.samzuhalsetiawan.floss.domain.manager

interface PermissionManager {
   enum class Permission {
      READ_USER_MEDIA_AUDIO,
   }
   fun checkIfPermissionGranted(permission: Permission): Boolean
}
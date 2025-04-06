package com.samzuhalsetiawan.floss.domain.manager

interface PermissionManager {

   fun registerPermissionHandler(handler: Handler)

   fun unregisterPermissionHandler(handler: Handler)

   suspend fun requestPermission(permission: Permission): PermissionResult

   fun onPermissionResult(permission: Permission, result: PermissionResult)

   fun checkIfPermissionGranted(permission: Permission): Boolean

   interface Handler {
      fun onHandlePermissionRequest(permission: Permission)
   }

}

enum class Permission {
   READ_USER_MEDIA_AUDIO,
}

enum class PermissionResult {
   GRANTED,
   DENIED,
   DENIED_AND_DO_NOT_ASK_AGAIN
}

inline fun PermissionResult.onGranted(action: () -> Unit): PermissionResult {
   if (this == PermissionResult.GRANTED) action()
   return this
}

inline fun PermissionResult.onDenied(action: () -> Unit): PermissionResult {
   if (this == PermissionResult.DENIED) action()
   return this
}

inline fun PermissionResult.onDeniedAndDoNotAskAgain(action: () -> Unit): PermissionResult {
   if (this == PermissionResult.DENIED_AND_DO_NOT_ASK_AGAIN) action()
   return this
}
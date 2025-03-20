package com.samzuhalsetiawan.floss.di

import android.content.Context
import com.samzuhalsetiawan.floss.data.manager.PermissionManagerImpl
import com.samzuhalsetiawan.floss.data.manager.PlayerManagerImpl
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

class ManagersModule(
   private val applicationContext: Context
) {
   val permissionManager: PermissionManager by lazy {
      PermissionManagerImpl(applicationContext)
   }
   val playerManager: PlayerManager by lazy {
      PlayerManagerImpl(applicationContext)
   }
}
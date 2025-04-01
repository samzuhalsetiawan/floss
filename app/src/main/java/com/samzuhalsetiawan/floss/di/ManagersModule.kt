package com.samzuhalsetiawan.floss.di

import com.samzuhalsetiawan.floss.data.manager.PermissionManagerImpl
import com.samzuhalsetiawan.floss.data.manager.PlayerManagerImpl
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val managersModule = module {
   singleOf(::PermissionManagerImpl) bind PermissionManager::class
   singleOf(::PlayerManagerImpl) bind PlayerManager::class
}
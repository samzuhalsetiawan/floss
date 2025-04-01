package com.samzuhalsetiawan.floss.di

import com.samzuhalsetiawan.floss.presentation.MainActivityViewModel
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreenViewModel
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.PermissionRequestScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
   viewModelOf(::MainActivityViewModel)
   viewModelOf(::PermissionRequestScreenViewModel)
   viewModelOf(::MusicListScreenViewModel)
}
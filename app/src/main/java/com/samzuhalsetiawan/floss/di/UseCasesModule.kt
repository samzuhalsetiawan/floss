package com.samzuhalsetiawan.floss.di

import com.samzuhalsetiawan.floss.domain.usecase.CheckIfPermissionGranted
import com.samzuhalsetiawan.floss.domain.usecase.DecideStartDestination
import com.samzuhalsetiawan.floss.domain.usecase.GetMusics
import com.samzuhalsetiawan.floss.domain.usecase.ListenToPlayerEvent
import com.samzuhalsetiawan.floss.domain.usecase.MarkAsNotFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.PauseMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayNextMusic
import com.samzuhalsetiawan.floss.domain.usecase.PlayPreviousMusic
import com.samzuhalsetiawan.floss.domain.usecase.ReleasePlayerResources
import com.samzuhalsetiawan.floss.domain.usecase.ReloadMusics
import com.samzuhalsetiawan.floss.domain.usecase.RequestReadAudioFilesPermission
import com.samzuhalsetiawan.floss.domain.usecase.ResumeMusic
import com.samzuhalsetiawan.floss.domain.usecase.SetRepeatMode
import com.samzuhalsetiawan.floss.domain.usecase.SetShuffleModeEnabled
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCasesModule = module {
   singleOf(::CheckIfPermissionGranted)
   singleOf(::DecideStartDestination)
   singleOf(::GetMusics)
   singleOf(::ListenToPlayerEvent)
   singleOf(::MarkAsNotFirstLaunch)
   singleOf(::PauseMusic)
   singleOf(::PlayAllMusic)
   singleOf(::PlayNextMusic)
   singleOf(::PlayPreviousMusic)
   singleOf(::ReleasePlayerResources)
   singleOf(::ReloadMusics)
   singleOf(::RequestReadAudioFilesPermission)
   singleOf(::ResumeMusic)
   singleOf(::SetRepeatMode)
   singleOf(::SetShuffleModeEnabled)
}
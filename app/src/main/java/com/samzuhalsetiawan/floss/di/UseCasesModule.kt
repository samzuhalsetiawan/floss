package com.samzuhalsetiawan.floss.di

import com.samzuhalsetiawan.floss.domain.usecase.GetAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.GetIsFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.SetIsFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.PlayerUseCases
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.ReleasePlayerResources
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCasesModule = module {
   singleOf(::GetAllMusic)
   singleOf(::PlayerUseCases)
   singleOf(::ReleasePlayerResources)
   singleOf(::GetIsFirstLaunch)
   singleOf(::SetIsFirstLaunch)
}
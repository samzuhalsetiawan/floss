package com.samzuhalsetiawan.floss.di

import com.samzuhalsetiawan.floss.data.repository.MusicRepositoryImpl
import com.samzuhalsetiawan.floss.data.repository.PreferencesRepositoryImpl
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import com.samzuhalsetiawan.floss.domain.repository.PreferencesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoriesModule = module {
   singleOf(::MusicRepositoryImpl) bind MusicRepository::class
   singleOf(::PreferencesRepositoryImpl) bind PreferencesRepository::class
}
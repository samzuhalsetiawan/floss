package com.samzuhalsetiawan.floss.di

import android.content.Context
import com.samzuhalsetiawan.floss.data.repository.MusicRepositoryImpl
import com.samzuhalsetiawan.floss.data.repository.PreferencesRepositoryImpl
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import com.samzuhalsetiawan.floss.domain.repository.PreferencesRepository

class RepositoriesModule(
   private val applicationContext: Context
) {
   val musicRepository: MusicRepository by lazy {
      MusicRepositoryImpl(applicationContext)
   }
   val preferencesRepository: PreferencesRepository by lazy {
      PreferencesRepositoryImpl(applicationContext)
   }
}
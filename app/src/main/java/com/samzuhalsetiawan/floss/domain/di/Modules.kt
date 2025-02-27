package com.samzuhalsetiawan.floss.domain.di

import android.content.Context
import com.samzuhalsetiawan.floss.data.repository.MusicRepositoryImpl
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository

class Modules(
   private val applicationContext: Context
) {
   val musicRepository: MusicRepository by lazy {
      MusicRepositoryImpl(applicationContext)
   }
}
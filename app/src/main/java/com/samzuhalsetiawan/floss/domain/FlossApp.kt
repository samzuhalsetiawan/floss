package com.samzuhalsetiawan.floss.domain

import android.app.Application
import com.samzuhalsetiawan.floss.data.repository.MusicRepositoryImpl
import com.samzuhalsetiawan.floss.domain.di.Modules
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository

class FlossApp : Application() {

   lateinit var modules: Modules

   override fun onCreate() {
      super.onCreate()
      modules = Modules(this)
   }
}
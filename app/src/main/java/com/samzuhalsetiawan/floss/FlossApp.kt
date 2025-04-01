package com.samzuhalsetiawan.floss

import android.app.Application
import com.samzuhalsetiawan.floss.di.managersModule
import com.samzuhalsetiawan.floss.di.repositoriesModule
import com.samzuhalsetiawan.floss.di.useCasesModule
import com.samzuhalsetiawan.floss.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlossApp : Application() {

   override fun onCreate() {
      super.onCreate()
      startKoin {
         androidContext(this@FlossApp)
         modules(
            repositoriesModule,
            managersModule,
            useCasesModule,
            viewModelsModule
         )
      }
   }
}
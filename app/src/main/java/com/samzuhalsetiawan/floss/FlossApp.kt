package com.samzuhalsetiawan.floss

import android.app.Application
import com.samzuhalsetiawan.floss.di.ManagersModule
import com.samzuhalsetiawan.floss.di.RepositoriesModule
import com.samzuhalsetiawan.floss.di.UseCasesModule

class FlossApp : Application() {

   lateinit var repositoriesModule: RepositoriesModule
   lateinit var useCasesModule: UseCasesModule
   lateinit var managersModule: ManagersModule

   override fun onCreate() {
      super.onCreate()
      repositoriesModule = RepositoriesModule(this)
      managersModule = ManagersModule(this)
      useCasesModule = UseCasesModule(this, managersModule, repositoriesModule)
   }

}
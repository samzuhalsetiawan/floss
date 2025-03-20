package com.samzuhalsetiawan.floss.di

import android.content.Context
import com.samzuhalsetiawan.floss.domain.usecase.GetAllMusic
import com.samzuhalsetiawan.floss.domain.usecase.GetIsFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.SetIsFirstLaunch
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.PlayerUseCases
import com.samzuhalsetiawan.floss.domain.usecase.playerusecase.ReleasePlayerResources

class UseCasesModule(
   private val applicationContext: Context,
   private val managersModule: ManagersModule,
   private val repositoriesModule: RepositoriesModule
) {
   val getAllMusic: GetAllMusic by lazy {
      GetAllMusic(
         musicRepository = repositoriesModule.musicRepository,
         permissionManager = managersModule.permissionManager
      )
   }
   val playerUseCases: PlayerUseCases by lazy {
      PlayerUseCases(
         playerManager = managersModule.playerManager
      )
   }
   val releasePlayerResources: ReleasePlayerResources by lazy {
      ReleasePlayerResources(
         playerManager = managersModule.playerManager
      )
   }
   val getIsFirstLaunch: GetIsFirstLaunch by lazy {
      GetIsFirstLaunch(
         preferencesRepository = repositoriesModule.preferencesRepository
      )
   }
   val setIsFirstLaunch: SetIsFirstLaunch by lazy {
      SetIsFirstLaunch(
         preferencesRepository = repositoriesModule.preferencesRepository
      )
   }
}
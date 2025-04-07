package com.samzuhalsetiawan.floss.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val coroutinesModule = module {

   single<CoroutineScope> {
      CoroutineScope(SupervisorJob())
   }

   single<CoroutineDispatcher> {
      Dispatchers.IO
   }

}
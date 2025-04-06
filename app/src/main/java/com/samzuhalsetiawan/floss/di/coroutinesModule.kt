package com.samzuhalsetiawan.floss.di

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.dsl.module

val coroutinesModule = module {

   single<CoroutineScope> {
      CoroutineScope(SupervisorJob())
   }

   single<CoroutineDispatcher> {
      Dispatchers.IO
   }

}
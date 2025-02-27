package com.samzuhalsetiawan.floss.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityVM: ViewModel() {

   var isAppReady = false

   init {
      viewModelScope.launch {
         delay(3000L)
         isAppReady = true
      }
   }

}
package com.samzuhalsetiawan.floss.presentation

sealed interface MainActivityEvent {
   data object OnActivityDestroyed : MainActivityEvent
}
package com.samzuhalsetiawan.floss.presentation.screen.onboardingscreen

sealed interface OnboardingScreenNavigationEvent {
   data object NavigateToPermissionRequestScreen: OnboardingScreenNavigationEvent
}
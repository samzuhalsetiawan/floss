package com.samzuhalsetiawan.floss.presentation.screen.onboardingscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText

@Composable
fun Onboarding2(
   modifier: Modifier = Modifier,
) {
   Box(
      modifier = modifier,
      contentAlignment = Alignment.Center
   ) {
      NormalText("Onboarding 2")
   }
}
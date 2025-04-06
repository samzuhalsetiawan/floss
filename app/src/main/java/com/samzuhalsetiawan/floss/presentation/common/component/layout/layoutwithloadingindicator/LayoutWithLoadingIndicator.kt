package com.samzuhalsetiawan.floss.presentation.common.component.layout.layoutwithloadingindicator

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LayoutWithLoadingIndicator(
   isLoading: Boolean,
   modifier: Modifier = Modifier,
   content: @Composable () -> Unit
) {
   AnimatedContent(
      modifier = modifier,
      targetState = isLoading,
      contentAlignment = Alignment.Center,
      label = "loading_transition"
   ) { targetState ->
      if (targetState) {
         Box(
            modifier = Modifier.fillMaxSize()
         ) {
            CircularProgressIndicator(
               modifier = Modifier
                  .align(Alignment.Center)
            )
         }
      } else {
         content()
      }
   }
}
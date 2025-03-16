package com.samzuhalsetiawan.floss.presentation.screen.onboardingscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.button.nextbutton.NextButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.prevbutton.PrevButton
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
   modifier: Modifier = Modifier,
   onNavigationEvent: (OnboardingScreenNavigationEvent) -> Unit
) {
   val pagerState = rememberPagerState(pageCount = { 3 })
   val scrollScope = rememberCoroutineScope()
   Box(
      modifier = modifier
   ) {
      HorizontalPager(
         modifier = Modifier.fillMaxSize(),
         state = pagerState
      ) { currentPageIndex ->
         when (currentPageIndex) {
            0 -> Onboarding1(modifier = Modifier.fillMaxSize())
            1 -> Onboarding2(modifier = Modifier.fillMaxSize())
            2 -> Onboarding3(modifier = Modifier.fillMaxSize())
         }
      }
      Row(
         modifier = Modifier.fillMaxWidth()
            .align(Alignment.BottomCenter),
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         PrevButton(
            onClick = {
               if (pagerState.canScrollBackward && pagerState.currentPage > 0) {
                  scrollScope.launch {
                     pagerState.animateScrollToPage(pagerState.currentPage - 1)
                  }
               }
            }
         )
         NextButton(
            onClick = {
               if (pagerState.currentPage == 2) {
                  onNavigationEvent(OnboardingScreenNavigationEvent.NavigateToPermissionRequestScreen)
               } else if (pagerState.canScrollForward && pagerState.currentPage < 2) {
                  scrollScope.launch {
                     pagerState.animateScrollToPage(pagerState.currentPage + 1)
                  }
               }
            }
         )
      }
   }
}
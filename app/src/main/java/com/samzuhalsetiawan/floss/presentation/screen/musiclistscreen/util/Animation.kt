package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util

import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.Alignment

val bottomFloatingMusicListItemEnterTransition = slideInVertically {
   it / 2
} + expandVertically(
   expandFrom = Alignment.Bottom
)
val bottomFloatingMusicListItemExitTransition = slideOutVertically {
   it / 2
} + shrinkVertically(
   shrinkTowards = Alignment.Bottom
)
val topFloatingMusicListItemEnterTransition = slideInVertically {
   -it / 2
} + expandVertically(
   expandFrom = Alignment.Top
)
val topFloatingMusicListItemExitTransition = slideOutVertically {
   -it / 2
} + shrinkVertically(
   shrinkTowards = Alignment.Top
)
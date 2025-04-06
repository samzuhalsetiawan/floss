package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.layoutwithfloatingmusiclistitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.domain.util.printlnOnEach
import com.samzuhalsetiawan.floss.presentation.common.model.Music
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreenEvent
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.MusicListScreenState
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.bottomFloatingMusicListItemEnterTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.bottomFloatingMusicListItemExitTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.topFloatingMusicListItemEnterTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.topFloatingMusicListItemExitTransition



@Composable
fun MusicListWithFloatingMusicListItem(
   modifier: Modifier = Modifier,
   state: MusicListScreenState,
   onEvent: (MusicListScreenEvent) -> Unit,
   musics: List<Music>,
   musicListItem: @Composable (music: Music) -> Unit
) {
   val lazyColumnState = rememberLazyListState()
   LaunchedEffect(lazyColumnState, state.currentMusic) {
      snapshotFlow { lazyColumnState.layoutInfo }
         .collect { layoutInfo ->
            if (state.currentMusic == null) return@collect
            val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull() ?: return@collect
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@collect
            val currentActiveMusicIndex = musics.indexOf(state.currentMusic)
            when {
               firstVisibleItem.index == currentActiveMusicIndex + 1 -> {
                  onEvent(MusicListScreenEvent.OnCurrentMusicListItemScrolledOutOffVisibleScreen(Alignment.Top))
               }
               lastVisibleItem.index == currentActiveMusicIndex - 1 -> {
                  onEvent(MusicListScreenEvent.OnCurrentMusicListItemScrolledOutOffVisibleScreen(Alignment.Bottom))
               }
               firstVisibleItem.index == currentActiveMusicIndex || lastVisibleItem.index == currentActiveMusicIndex -> {
                  onEvent(MusicListScreenEvent.OnCurrentMusicListItemScrolledBackIntoVisibleScreen)
               }
            }
         }
   }

   Box(
      modifier = modifier
   ) {
      LazyColumn(
         modifier = Modifier.fillMaxSize(),
         state = lazyColumnState
      ) {
         items(
            items = musics,
            key = { music -> music.id },
         ) {music ->
            musicListItem(music)
            androidx.compose.animation.AnimatedVisibility(
               visible = music.id != state.currentMusic?.id
            ) {
               HorizontalDivider(
                  modifier = Modifier
                     .padding(horizontal = 12.dp, vertical = 4.dp)
               )
            }
         }
      }
      androidx.compose.animation.AnimatedVisibility(
         modifier = Modifier
            .align(
               alignment = when (state.floatingMusicListItemPosition) {
                  Alignment.Top -> Alignment.TopCenter
                  Alignment.Bottom -> Alignment.BottomCenter
                  else -> throw IllegalStateException()
               }
            ),
         visible = state.isFloatingMusicListItemShowed,
         enter = when (state.floatingMusicListItemPosition) {
            Alignment.Top -> topFloatingMusicListItemEnterTransition
            Alignment.Bottom -> bottomFloatingMusicListItemEnterTransition
            else -> throw IllegalStateException()
         },
         exit = when (state.floatingMusicListItemPosition) {
            Alignment.Top -> topFloatingMusicListItemExitTransition
            Alignment.Bottom -> bottomFloatingMusicListItemExitTransition
            else -> throw IllegalStateException()
         }
      ) {
         state.currentMusic?.let { musicListItem(it) }
      }
   }
}
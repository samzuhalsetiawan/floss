package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.layoutwithfloatingmusiclistitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.presentation.common.model.Music
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.bottomFloatingMusicListItemEnterTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.bottomFloatingMusicListItemExitTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.topFloatingMusicListItemEnterTransition
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.util.topFloatingMusicListItemExitTransition

@Composable
fun MusicListWithFloatingMusicListItem(
   modifier: Modifier = Modifier,
   state: MusicListWithFloatingMusicListItemState = rememberMusicListWithFloatingMusicListItemState(),
   currentMusic: Music?,
   musics: List<Music>,
   musicListItem: @Composable (music: Music) -> Unit
) {
   val lazyColumnState = rememberLazyListState()

   DetectIfActiveMusicListItemOutOffScreen(
      lazyListState = lazyColumnState,
      currentMusic = currentMusic,
      musics = musics,
      onOutFromTop = { state.onCurrentMusicListItemScrolledOutOffVisibleScreen(Alignment.Top) },
      onOutFromBottom = { state.onCurrentMusicListItemScrolledOutOffVisibleScreen(Alignment.Bottom) },
      onBackIntoScreen = { state.onCurrentMusicListItemScrolledBackIntoVisibleScreen() }
   )
   Box(modifier) {
      MusicList(lazyColumnState, musics) { music ->
         musicListItem(music)
         DividerLine(music.id != currentMusic?.id)
      }
      FloatingMusicListItem(
         visible = state.isFloatingMusicListItemShowed,
         position = state.floatingMusicListItemPosition
      ) {
         currentMusic?.let { musicListItem(it) }
      }
   }
}

@Composable
private fun BoxScope.FloatingMusicListItem(
   visible: Boolean,
   position: Alignment.Vertical,
   musicListItem: @Composable () -> Unit,
) {
   androidx.compose.animation.AnimatedVisibility(
      modifier = Modifier
         .align(
            alignment = when (position) {
               Alignment.Top -> Alignment.TopCenter
               Alignment.Bottom -> Alignment.BottomCenter
               else -> throw IllegalStateException()
            }
         ),
      visible = visible,
      enter = when (position) {
         Alignment.Top -> topFloatingMusicListItemEnterTransition
         Alignment.Bottom -> bottomFloatingMusicListItemEnterTransition
         else -> throw IllegalStateException()
      },
      exit = when (position) {
         Alignment.Top -> topFloatingMusicListItemExitTransition
         Alignment.Bottom -> bottomFloatingMusicListItemExitTransition
         else -> throw IllegalStateException()
      }
   ) {
      musicListItem()
   }
}

@Composable
private fun DividerLine(
   visible: Boolean = true
) {
   androidx.compose.animation.AnimatedVisibility(
      visible = visible
   ) {
      HorizontalDivider(
         modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
      )
   }
}

@Composable
private fun MusicList(
   state: LazyListState,
   musics: List<Music>,
   musicListItem: @Composable LazyItemScope.(music: Music) -> Unit
) {
   LazyColumn(
      modifier = Modifier.fillMaxSize(),
      state = state
   ) {
      items(
         items = musics,
         key = { music -> music.id },
         itemContent = musicListItem,
      )
   }
}

@Composable
private fun DetectIfActiveMusicListItemOutOffScreen(
   lazyListState: LazyListState,
   currentMusic: Music?,
   musics: List<Music>,
   onOutFromTop: () -> Unit,
   onOutFromBottom: () -> Unit,
   onBackIntoScreen: () -> Unit
) {
   return LaunchedEffect(lazyListState, currentMusic) {
      snapshotFlow { lazyListState.layoutInfo }
         .collect { layoutInfo ->
            if (currentMusic == null) return@collect
            val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull() ?: return@collect
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@collect
            val currentActiveMusicIndex = musics.indexOf(currentMusic)
            when {
               firstVisibleItem.index == currentActiveMusicIndex + 1 -> onOutFromTop()
               lastVisibleItem.index == currentActiveMusicIndex - 1 -> onOutFromBottom()
               firstVisibleItem.index == currentActiveMusicIndex || lastVisibleItem.index == currentActiveMusicIndex -> onBackIntoScreen()
            }
         }
   }
}

class MusicListWithFloatingMusicListItemState {
   var isFloatingMusicListItemShowed: Boolean by mutableStateOf(false)
      private set
   var floatingMusicListItemPosition: Alignment.Vertical by mutableStateOf(Alignment.Bottom)
      private set
   fun onCurrentMusicListItemScrolledOutOffVisibleScreen(outLocation: Alignment.Vertical) {
      isFloatingMusicListItemShowed = true
      floatingMusicListItemPosition = outLocation
   }
   fun onCurrentMusicListItemScrolledBackIntoVisibleScreen() {
      isFloatingMusicListItemShowed = false
   }
}

@Composable
fun rememberMusicListWithFloatingMusicListItemState(): MusicListWithFloatingMusicListItemState {
   return remember {
      MusicListWithFloatingMusicListItemState()
   }
}
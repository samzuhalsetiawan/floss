package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager.RepeatMode
import com.samzuhalsetiawan.floss.presentation.common.component.albumart.AlbumArtSmall
import com.samzuhalsetiawan.floss.presentation.common.component.button.nextbutton.NextButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.pausebutton.PauseButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.playbutton.PlayButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.prevbutton.PrevButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.repeatbutton.RepeatButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.shufflebutton.ShuffleButton
import com.samzuhalsetiawan.floss.presentation.common.component.musicprogressbar.MusicProgressBar
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.common.component.text.subtext.SubText
import com.samzuhalsetiawan.floss.presentation.common.model.Music
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun MusicList(
   modifier: Modifier = Modifier,
   expanded: Boolean,
   music: Music?,
   isPlaying: Boolean = false,
   isShuffleOn: Boolean = false,
   repeatMode: RepeatMode = RepeatMode.NONE,
   onPlayButtonClick: () -> Unit = {},
   onPauseButtonClick: () -> Unit = {},
   onPrevButtonClick: () -> Unit = {},
   onNextButtonClick: () -> Unit = {},
   onShuffleButtonClick: (Boolean) -> Unit = {},
   onRepeatButtonClick: (RepeatMode) -> Unit = {},
   onMoreOptionButtonClick: () -> Unit = {},
   onFullscreenButtonClick: () -> Unit = {}
) {
   ElevatedCard(
      modifier = modifier
         .animateContentSize()
         .then(
            if (expanded) {
               Modifier
                  .padding(14.dp)
                  .shadow(
                     elevation = 1.dp,
                     clip = false,
                     shape = CardDefaults.shape,
                  )
            } else {
               Modifier
                  .padding(2.dp)
            }
         ),
      onClick = {
         if (!expanded) onPlayButtonClick()
      },
      shape = if (expanded) CardDefaults.shape else RectangleShape,
      colors = if (expanded) CardDefaults.elevatedCardColors() else CardDefaults.outlinedCardColors(),
      elevation = if (expanded) CardDefaults.elevatedCardElevation() else CardDefaults.outlinedCardElevation()
   ) {
      Row(
         modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically
      ) {
         AlbumArtSmall(
            modifier = Modifier
               .padding(start = 12.dp)
               .size(if (expanded) 84.dp else 48.dp),
            albumArt = music?.albumArt
         )
         Column(
            verticalArrangement = Arrangement.Center
         ) {
            Row(
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
            ) {
               Column(
                  modifier = Modifier
                     .padding(start = 12.dp, bottom = if (expanded) 12.dp else 0.dp)
                     .weight(1f),
                  verticalArrangement = Arrangement.Center
               ) {
                  NormalText(
                     text = music?.title ?: "___ / ___",
                  )
                  if (expanded) {
                     SubText(
                        text = music?.title ?: "___ / ___"
                     )
                  }
               }
               if (expanded) {
                  IconButton(
                     onClick = onFullscreenButtonClick
                  ) {
                     Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = null /*TODO: add content description */
                     )
                  }
               } else {
                  IconButton(
                     onClick = onMoreOptionButtonClick
                  ) {
                     Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null /*TODO: add content description */,
                     )
                  }
               }
            }
            if (expanded) {
               MusicProgressBar(
                  modifier = Modifier
                     .padding(horizontal = 12.dp)
                     .fillMaxWidth(),
                  currentDuration = 15000L,
                  maxDuration = 30000L,
                  onSeekTo = {}
               )
               MusicControlButtons(
                  isShuffleOn = isShuffleOn,
                  isPlaying = isPlaying,
                  repeatMode = repeatMode,
                  onShuffleButtonClick = onShuffleButtonClick,
                  onPrevButtonClick = onPrevButtonClick,
                  onPlayButtonClick = onPlayButtonClick,
                  onPauseButtonClick = onPauseButtonClick,
                  onNextButtonClick = onNextButtonClick,
                  onRepeatButtonClick = onRepeatButtonClick
               )
            }
         }
      }
   }
}

@Composable
private fun MusicControlButtons(
   isShuffleOn: Boolean,
   isPlaying: Boolean,
   repeatMode: RepeatMode,
   onShuffleButtonClick: (Boolean) -> Unit,
   onPrevButtonClick: () -> Unit,
   onPlayButtonClick: () -> Unit,
   onPauseButtonClick: () -> Unit,
   onNextButtonClick: () -> Unit,
   onRepeatButtonClick: (RepeatMode) -> Unit
) {
   Row(
      modifier = Modifier
         .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
   ) {
      ShuffleButton(
         isShuffleOn = isShuffleOn,
         onClick = { onShuffleButtonClick(it) }
      )
      PrevButton(
         onClick = onPrevButtonClick
      )
      if (isPlaying) {
         PauseButton(
            onClick = onPauseButtonClick
         )
      } else {
         PlayButton(
            onClick = onPlayButtonClick
         )
      }
      NextButton(
         onClick = onNextButtonClick
      )
      RepeatButton(
         repeatMode = repeatMode,
         onClick = { onRepeatButtonClick(it) }
      )
   }
}


@Preview(showBackground = true)
@Composable
private fun MusicListPreview() {
   FlossTheme {
      MusicList(
         modifier = Modifier.padding(14.dp),
         expanded = true,
         music = Music(
            id = "1",
            title = "Title",
            uri = Uri.EMPTY,
            displayName = "Display Name",
            albumArt = null
         ),
      )
   }
}
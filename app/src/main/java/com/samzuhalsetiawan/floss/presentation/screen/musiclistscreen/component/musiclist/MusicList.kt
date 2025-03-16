package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist

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
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.util.dummy.DummyData
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.common.component.text.subtext.SubText
import com.samzuhalsetiawan.floss.presentation.common.component.musicprogressbar.MusicProgressBar
import com.samzuhalsetiawan.floss.presentation.common.component.button.nextbutton.NextButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.prevbutton.PrevButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.repeatbutton.RepeatButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.repeatbutton.RepeatMode
import com.samzuhalsetiawan.floss.presentation.common.component.button.shufflebutton.ShuffleButton
import com.samzuhalsetiawan.floss.presentation.common.component.albumart.AlbumArtSmall
import com.samzuhalsetiawan.floss.presentation.common.component.button.playbutton.PlayButton
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun MusicList(
   modifier: Modifier = Modifier,
   expanded: Boolean,
   music: Music,
   onPlayButtonClick: () -> Unit = {},
) {
   ElevatedCard(
      modifier = modifier
         .animateContentSize()
         .padding(2.dp)
         .then(
            if (expanded) {
               Modifier.shadow(
                  elevation = 1.dp,
                  clip = false,
                  shape = CardDefaults.shape,
               )
            } else Modifier
         ),
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
               .size(if (expanded) 84.dp else 48.dp)
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
                     text = music.title,
                  )
                  if (expanded) {
                     SubText(
                        text = music.title
                     )
                  }
               }
               if (expanded) {
                  IconButton(
                     onClick = {}
                  ) {
                     Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = null /*TODO: add content description */
                     )
                  }
               } else {
                  IconButton(
                     onClick = onPlayButtonClick
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
               Row(
                  modifier = Modifier
                     .fillMaxWidth(),
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  ShuffleButton(isShuffleOn = false)
                  PrevButton()
                  PlayButton(onClick = onPlayButtonClick)
                  NextButton()
                  RepeatButton(repeatMode = RepeatMode.OFF)
               }
            }
         }
      }
   }
}


@Preview(
   showBackground = true,
//   backgroundColor = 0xFF0000
)
@Composable
private fun MusicListPreview() {
   FlossTheme {
      MusicList(
         modifier = Modifier.padding(14.dp),
         expanded = true,
         music = DummyData.musics.first(),
      )
   }
}
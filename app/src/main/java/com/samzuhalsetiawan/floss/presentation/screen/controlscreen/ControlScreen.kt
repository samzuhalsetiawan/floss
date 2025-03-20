package com.samzuhalsetiawan.floss.presentation.screen.controlscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.presentation.common.component.text.headlinetext.HeadlineText
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.common.component.albumart.AlbumArt
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.header.Header
import com.samzuhalsetiawan.floss.presentation.common.component.musicprogressbar.MusicProgressBar
import com.samzuhalsetiawan.floss.presentation.common.component.button.nextbutton.NextButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.playbutton.PlayButtonLarge
import com.samzuhalsetiawan.floss.presentation.common.component.button.prevbutton.PrevButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.repeatbutton.RepeatButton
import com.samzuhalsetiawan.floss.presentation.common.component.button.shufflebutton.ShuffleButton
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun ControlScreen() {
   Scaffold(
      topBar = {
         Header()
      }
   ) { scaffoldPadding ->
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
      ) {
         Box(
            modifier = Modifier
               .fillMaxWidth()
               .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
         ) {
            AlbumArt(modifier = Modifier.size(200.dp))
         }
         Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
         ) {
            HeadlineText("Title")
            NormalText("Artist")
         }
         MusicProgressBar(
            modifier = Modifier.fillMaxWidth()
               .padding(top = 14.dp)
               .padding(horizontal = 14.dp),
            currentDuration = 6000L,
            maxDuration = 120000L,
            onSeekTo = {}
         )
         Row(
            modifier = Modifier.fillMaxWidth()
               .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
         ) {
//            RepeatButton(repeatMode = RepeatMode.OFF)
            PrevButton()
            PlayButtonLarge()
            NextButton()
//            ShuffleButton(isShuffleOn = false)
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun ControlScreenPreview() {
   FlossTheme {
      ControlScreen()
   }
}
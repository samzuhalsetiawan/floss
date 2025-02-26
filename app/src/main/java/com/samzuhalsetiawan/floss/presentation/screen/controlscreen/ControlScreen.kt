package com.samzuhalsetiawan.floss.presentation.screen.controlscreen

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.presentation.common.component.text.headlinetext.HeadlineText
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.albumart.AlbumArt
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.header.Header
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.musicprogressbar.MusicProgressBar
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.nextbutton.NextButton
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.playbutton.PlayButton
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.prevbutton.PrevButton
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.repeatbutton.RepeatButton
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.repeatbutton.RepeatMode
import com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.shufflebutton.ShuffleButton
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

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
            RepeatButton(repeatMode = RepeatMode.OFF)
            PrevButton()
            PlayButton()
            NextButton()
            ShuffleButton(isShuffleOn = false)
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
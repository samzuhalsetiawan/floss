package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.util.dummy.DummyData
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.component.albumart.MiniAlbumArt
import com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen.component.musiclist.component.playbutton.PlayButton
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun MusicList(
   modifier: Modifier = Modifier,
   music: Music,
) {
   ListItem(
      modifier = modifier,
      leadingContent = {
         MiniAlbumArt(
            modifier = Modifier
               .size(32.dp)
         )
      },
      headlineContent = {
         NormalText(music.title)
      },
      trailingContent = {
         PlayButton()
      }
   )
}

@Preview(showBackground = true)
@Composable
private fun MusicListPreview() {
   FlossTheme {
      MusicList(
         music = DummyData.musics.first()
      )
   }
}
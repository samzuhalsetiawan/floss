package com.samzuhalsetiawan.floss.domain.util.dummy

import android.net.Uri
import com.samzuhalsetiawan.floss.domain.model.Music
import java.util.UUID

object DummyData {
   val musics = List(10) {
      Music(
         id = UUID.randomUUID().toString(),
         title = "Music Example $it",
         uri = Uri.EMPTY,
         displayName = "MusicExample$it.mp3",
         relativePath = "relative/path/to/music$it.mp3",
         data = "data/to/music$it.mp3"
      )
   }
}
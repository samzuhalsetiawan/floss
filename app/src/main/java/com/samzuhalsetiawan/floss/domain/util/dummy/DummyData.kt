package com.samzuhalsetiawan.floss.domain.util.dummy

import com.samzuhalsetiawan.floss.domain.model.Music
import java.util.UUID

object DummyData {
   val musics = List(10) {
      Music(
         id = UUID.randomUUID().toString(),
         title = "Music Example $it"
      )
   }
}
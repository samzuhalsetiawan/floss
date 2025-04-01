package com.samzuhalsetiawan.floss.domain.manager

import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.StateFlow

interface PlayerManager {

   fun play(music: Music)

   fun play(musics: List<Music>, startPosition: Int)

   fun resume()

   fun pause()

   fun next()

   fun previous()

   val isPlaying: StateFlow<Boolean>

   val shuffleEnabled: StateFlow<Boolean>

   fun setShuffleEnabled(shuffleEnabled: Boolean)

   val currentMusicId: StateFlow<String?>

   val repeatMode: StateFlow<RepeatMode>

   fun setRepeatMode(repeatMode: RepeatMode)

   fun destroy()

   enum class RepeatMode {
      NONE,
      ONE,
      ALL
   }

}
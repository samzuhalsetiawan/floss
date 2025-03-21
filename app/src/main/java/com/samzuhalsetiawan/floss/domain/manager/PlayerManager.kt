package com.samzuhalsetiawan.floss.domain.manager

import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface PlayerManager {

   fun play(music: Music)

   fun pause()

   fun next()

   fun previous()

   val isPlaying: StateFlow<Boolean>

   val shuffleEnabled: StateFlow<Boolean>

   fun setShuffleEnabled(shuffleEnabled: Boolean)

   val currentMusic: StateFlow<Music?>

   val repeatMode: StateFlow<RepeatMode>

   fun setRepeatMode(repeatMode: RepeatMode)

   fun destroy()

   enum class RepeatMode {
      NONE,
      ONE,
      ALL
   }

   data class PlayerState(
      val isPlaying: Boolean,
      val shuffleEnabled: Boolean,
      val currentMusic: Music?,
      val repeatMode: RepeatMode
   )
}
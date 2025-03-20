package com.samzuhalsetiawan.floss.domain.manager

import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.SharedFlow

interface PlayerManager {

   fun play(music: Music)

   fun pause()

   fun next()

   fun previous()

   val isPlaying: Boolean

   val isPlayingFlow: SharedFlow<Boolean>

   var shuffleEnabled: Boolean

   val shuffleEnabledFlow: SharedFlow<Boolean>

   val currentMusic: Music?

   val currentMusicFlow: SharedFlow<Music?>

   var repeatMode: RepeatMode

   val repeatModeFlow: SharedFlow<RepeatMode>

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
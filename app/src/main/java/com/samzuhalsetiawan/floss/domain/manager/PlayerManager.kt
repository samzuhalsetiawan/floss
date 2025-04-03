package com.samzuhalsetiawan.floss.domain.manager

import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PlayerManager {

   fun play(music: Music)

   fun play(musics: List<Music>, startPosition: Int)

   fun resume()

   fun pause()

   fun next()

   fun previous()

   val isPlaying: Flow<Boolean>

   val shuffleEnabled: Flow<Boolean>

   fun setShuffleEnabled(shuffleEnabled: Boolean)

   val currentMusicId: Flow<String?>

   val repeatMode: Flow<RepeatMode>

   fun setRepeatMode(repeatMode: RepeatMode)

   fun destroy()

   enum class RepeatMode {
      NONE,
      ONE,
      ALL
   }

   interface Listener {
      fun onIsPlayingChanged(isPlaying: Boolean) = Unit
      fun onCurrentMusicChanged(currentMusicId: String?) = Unit
      fun onRepeatModeChanged(repeatMode: RepeatMode) = Unit
      fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) = Unit
   }

}
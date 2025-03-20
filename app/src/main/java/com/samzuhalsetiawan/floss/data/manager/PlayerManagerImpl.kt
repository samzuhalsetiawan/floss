package com.samzuhalsetiawan.floss.data.manager

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.samzuhalsetiawan.floss.data.service.BackgroundPlayerService
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager
import com.samzuhalsetiawan.floss.domain.model.Music
import androidx.core.net.toUri
import com.samzuhalsetiawan.floss.data.manager.util.toMedia3RepeatMode
import com.samzuhalsetiawan.floss.data.manager.util.toRepeatMode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class PlayerManagerImpl(
   private val applicationContext: Context
): PlayerManager, Player.Listener {

   private var mediaControllerFuture: ListenableFuture<MediaController>

   private val mediaController: MediaController?
      get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

   private val player: Player
      get() = requireNotNull(mediaController)

   private val _isPlayingFlow = MutableSharedFlow<Boolean>()

   private val _shuffleEnabledFlow = MutableSharedFlow<Boolean>()

   private val _currentMusicFlow = MutableSharedFlow<Music?>()

   private val _repeatModeFlow = MutableSharedFlow<PlayerManager.RepeatMode>()

   init {
      val sessionToken = SessionToken(applicationContext, ComponentName(applicationContext, BackgroundPlayerService::class.java))
      mediaControllerFuture = MediaController.Builder(applicationContext, sessionToken).buildAsync()
      mediaControllerFuture.addListener({
         player.addListener(this)
      }, MoreExecutors.directExecutor())
   }

   override fun play(music: Music) {
      val currentMusic = player.currentMediaItem?.localConfiguration?.tag as? Music
      if (currentMusic == music) return resume()
      val mediaItem = MediaItem.Builder()
         .setUri(music.uri.toUri())
         .setMediaId(music.id)
         .setTag(music)
         .build()
      player.setMediaItem(mediaItem)
      player.prepare()
      player.play()
   }

   override fun destroy() {
      MediaController.releaseFuture(mediaControllerFuture)
   }

   private fun resume() {
      player.play()
   }

   override fun pause() {
      player.pause()
   }

   override fun next() {
      player.seekToNext()
   }

   override fun previous() {
      player.seekToPrevious()
   }

   override val isPlaying: Boolean
      get() = player.isPlaying

   override val isPlayingFlow: SharedFlow<Boolean>
      get() = _isPlayingFlow

   override fun onIsPlayingChanged(isPlaying: Boolean) {
      _isPlayingFlow.tryEmit(isPlaying)
   }

   override var shuffleEnabled: Boolean
      get() = player.shuffleModeEnabled
      set(value) {
         player.shuffleModeEnabled = value
      }

   override val shuffleEnabledFlow: SharedFlow<Boolean>
      get() = _shuffleEnabledFlow

   override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
      _shuffleEnabledFlow.tryEmit(shuffleModeEnabled)
   }

   override val currentMusic: Music?
      get() {
         val mediaItem = player.currentMediaItem ?: return null
         return mediaItem.localConfiguration?.tag as? Music
      }

   override val currentMusicFlow: SharedFlow<Music?>
      get() = _currentMusicFlow

   override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
      _currentMusicFlow.tryEmit(mediaItem?.localConfiguration?.tag as? Music)
   }

   override var repeatMode: PlayerManager.RepeatMode
      get() = player.repeatMode.toRepeatMode()
      set(value) {
         player.repeatMode = value.toMedia3RepeatMode()
      }

   override val repeatModeFlow: SharedFlow<PlayerManager.RepeatMode>
      get() = _repeatModeFlow

   override fun onRepeatModeChanged(repeatMode: Int) {
      _repeatModeFlow.tryEmit(repeatMode.toRepeatMode())
   }
}
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerManagerImpl(
   private val applicationContext: Context
): PlayerManager, Player.Listener {

   private var mediaControllerFuture: ListenableFuture<MediaController>

   private val mediaController: MediaController?
      get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

   private val player: Player
      get() = requireNotNull(mediaController)

   private val _isPlaying = MutableStateFlow<Boolean>(false)

   private val _shuffleEnabled = MutableStateFlow<Boolean>(false)

   private val _currentMusic = MutableStateFlow<Music?>(null)

   private val _repeatMode = MutableStateFlow<PlayerManager.RepeatMode>(PlayerManager.RepeatMode.NONE)

   init {
      val sessionToken = SessionToken(applicationContext, ComponentName(applicationContext, BackgroundPlayerService::class.java))
      mediaControllerFuture = MediaController.Builder(applicationContext, sessionToken).buildAsync()
      mediaControllerFuture.addListener({
         player.addListener(this)
      }, MoreExecutors.directExecutor())
   }

   override fun play(music: Music) {
      val currentMusicId = player.currentMediaItem?.mediaId
      if (currentMusicId == music.id) return resume()
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

   override val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

   override fun onIsPlayingChanged(isPlaying: Boolean) {
      _isPlaying.update { isPlaying }
   }

   override val shuffleEnabled: StateFlow<Boolean> = _shuffleEnabled.asStateFlow()

   override fun setShuffleEnabled(shuffleEnabled: Boolean) {
      player.shuffleModeEnabled = shuffleEnabled
   }

   override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
      _shuffleEnabled.update { shuffleModeEnabled }
   }

   override val currentMusic: StateFlow<Music?> = _currentMusic.asStateFlow()

   override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
      // somehow this trigger twice when mediaItem change
      // and for the second one doesn't contain localConfiguration event though mediaItem is not null
      // so i decide to ignore the second event by return if localConfiguration is null
      if (mediaItem == null) return _currentMusic.update { null }
      val music = mediaItem.localConfiguration?.tag as? Music ?: return
      _currentMusic.update { music }
   }

   override val repeatMode: StateFlow<PlayerManager.RepeatMode> = _repeatMode.asStateFlow()

   override fun setRepeatMode(repeatMode: PlayerManager.RepeatMode) {
      player.repeatMode = repeatMode.toMedia3RepeatMode()
   }

   override fun onRepeatModeChanged(repeatMode: Int) {
      _repeatMode.update { repeatMode.toRepeatMode() }
   }
}
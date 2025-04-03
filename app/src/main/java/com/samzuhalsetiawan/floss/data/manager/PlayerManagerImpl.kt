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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerManagerImpl(
   private val applicationContext: Context
): PlayerManager, Player.Listener {

   private var mediaControllerFuture: ListenableFuture<MediaController>? = null

   private val mediaController: MediaController?
      get() = mediaControllerFuture?.let { if (it.isDone) it.get() else null }

   private val player: Player
      get() = requireNotNull(mediaController)

   private val _isPlaying = MutableStateFlow<Boolean>(false)

   private val _shuffleEnabled = MutableStateFlow<Boolean>(false)

   private val _currentMusicId = MutableStateFlow<String?>(null)

   private val _repeatMode = MutableStateFlow<PlayerManager.RepeatMode>(PlayerManager.RepeatMode.NONE)

   init {
      initializeMediaController()
   }

   private fun initializeMediaController() {
      val sessionToken = SessionToken(applicationContext, ComponentName(applicationContext, BackgroundPlayerService::class.java))
      mediaControllerFuture = MediaController.Builder(applicationContext, sessionToken)
         .buildAsync()
         .also {
            it.addListener(
               { player.addListener(this) },
               MoreExecutors.directExecutor()
            )
         }
   }

   override fun destroy() {
      mediaControllerFuture?.let { MediaController.releaseFuture(it) }
      mediaControllerFuture = null
   }

   override fun play(music: Music) {
      val mediaItem = MediaItem.Builder()
         .setUri(music.uri.toUri())
         .setMediaId(music.id)
         .build()
      player.setMediaItem(mediaItem)
      player.prepare()
      player.play()
   }

   override fun play(musics: List<Music>, startPosition: Int) {
      val mediaItems = musics.map {
         MediaItem.Builder()
            .setUri(it.uri.toUri())
            .setMediaId(it.id)
            .build()
      }
      player.setMediaItems(mediaItems, startPosition, 0)
      player.prepare()
      player.play()
   }

   override fun resume() {
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

   override val isPlaying: Flow<Boolean> = _isPlaying

   override fun onIsPlayingChanged(isPlaying: Boolean) {
      _isPlaying.update { isPlaying }
   }

   override val shuffleEnabled: Flow<Boolean> = _shuffleEnabled

   override fun setShuffleEnabled(shuffleEnabled: Boolean) {
      player.shuffleModeEnabled = shuffleEnabled
   }

   override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
      _shuffleEnabled.update { shuffleModeEnabled }
   }

   override val currentMusicId: Flow<String?> = _currentMusicId

   override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
      val musicId = mediaItem?.mediaId
      _currentMusicId.update { musicId }
   }

   override val repeatMode: Flow<PlayerManager.RepeatMode> = _repeatMode

   override fun setRepeatMode(repeatMode: PlayerManager.RepeatMode) {
      player.repeatMode = repeatMode.toMedia3RepeatMode()
   }

   override fun onRepeatModeChanged(repeatMode: Int) {
      _repeatMode.update { repeatMode.toRepeatMode() }
   }
}
package com.samzuhalsetiawan.floss.domain.service

import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class BackgroundPlayerService: MediaSessionService(), MediaSession.Callback {

//   private val supervisorJob = SupervisorJob()
//   private val coroutineScope = CoroutineScope(Dispatchers.Main + supervisorJob)
   private var mediaSession: MediaSession? = null

   override fun onCreate() {
      super.onCreate()
      val player = ExoPlayer.Builder(this)
         .setAudioAttributes(AudioAttributes.DEFAULT, false)
         .setHandleAudioBecomingNoisy(true)
         .build()
      mediaSession = MediaSession.Builder(this, player)
         .setCallback(this)
         .build()
   }

   override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
      return mediaSession
   }

//   @OptIn(UnstableApi::class)
//   override fun onPlaybackResumption(
//      mediaSession: MediaSession,
//      controller: MediaSession.ControllerInfo
//   ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
//      val settable = SettableFuture.create<MediaSession.MediaItemsWithStartPosition>()
//      coroutineScope.launch {
//         val mediaItems: List<MediaItem> = TODO("retrieve last played playlist from cache")
//         val startIndex: Int = TODO("retrieve last played music index from cache")
//         val startPosition: Long = TODO("retrieve last playing time of last played music from cache")
//         val resumptionPlaylist = MediaSession.MediaItemsWithStartPosition(mediaItems, startIndex, startPosition)
//         settable.set(resumptionPlaylist)
//      }
//      return settable
//   }

   @OptIn(UnstableApi::class)
   override fun onTaskRemoved(rootIntent: Intent?) {
      if (isPlaybackOngoing) {
         pauseAllPlayersAndStopSelf()
      }
      super.onTaskRemoved(rootIntent)
   }

   override fun onDestroy() {
      mediaSession?.run {
         player.release()
         release()
         mediaSession = null
      }
//      supervisorJob.cancel()
      super.onDestroy()
   }
}
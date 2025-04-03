package com.samzuhalsetiawan.floss.data.repository

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class MusicRepositoryImpl(
   private val applicationContext: Context
): MusicRepository {

   private val _musics: MutableStateFlow<List<Music>> = MutableStateFlow(emptyList())

   override val musics: Flow<List<Music>> = _musics
      .onStart { reloadMusics() }

   private suspend fun loadMusics(): List<Music> {
      val projection = arrayOf(
         MediaStore.Audio.AudioColumns._ID,
         MediaStore.Audio.AudioColumns.TITLE,
         MediaStore.Audio.AudioColumns.DISPLAY_NAME,
         MediaStore.Audio.AudioColumns.RELATIVE_PATH,
         MediaStore.Audio.AudioColumns.DATA,
      )
      val selection = "${MediaStore.Audio.Media.IS_MUSIC} != ?"
      val selectionArgs = arrayOf("0")
      return withContext(Dispatchers.IO) {
         applicationContext.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
         )?.use { cursor ->
            val musics = mutableListOf<Music>()
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            val relativePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.RELATIVE_PATH)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val mediaMetadataRetriever = MediaMetadataRetriever()
            while (cursor.moveToNext()) {
               val id = cursor.getLong(idColumn)
               val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
               mediaMetadataRetriever.setDataSource(applicationContext, uri)
               musics.add(
                  Music(
                     id = id.toString(),
                     title = cursor.getString(titleColumn),
                     uri = uri.toString(),
                     displayName = cursor.getString(displayNameColumn),
                     relativePath = cursor.getString(relativePathColumn),
                     data = cursor.getString(dataColumn),
                     albumArt = mediaMetadataRetriever.embeddedPicture,
                  )
               )
            }
            return@use musics.also { mediaMetadataRetriever.release() }
         } ?: throw Exception("Expecting a cursor but null is returned.")
      }
   }

   override suspend fun reloadMusics() {
      val musics = loadMusics()
      _musics.update { musics }
   }
}
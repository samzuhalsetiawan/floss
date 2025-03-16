package com.samzuhalsetiawan.floss.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MusicRepositoryImpl(
   private val applicationContext: Context
): MusicRepository {

   override suspend fun getAllMusics(): Result<List<Music>> {
      val projection = arrayOf(
         MediaStore.Audio.AudioColumns._ID,
         MediaStore.Audio.AudioColumns.TITLE,
         MediaStore.Audio.AudioColumns.DISPLAY_NAME,
         MediaStore.Audio.AudioColumns.RELATIVE_PATH,
         MediaStore.Audio.AudioColumns.DATA,
      )
      val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
      return try {
         applicationContext.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
         )?.use { cursor ->
            val musics = mutableListOf<Music>()
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            val relativePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.RELATIVE_PATH)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            while (cursor.moveToNext()) {
               val id = cursor.getLong(idColumn)
               val title = cursor.getString(titleColumn)
               val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
               val displayName = cursor.getString(displayNameColumn)
               val relativePath = cursor.getString(relativePathColumn)
               val data = cursor.getString(dataColumn)
               musics.add(Music(id.toString(), title, uri, displayName, relativePath, data))
            }
            Result.success(musics)
         } ?: throw Exception("Expecting contentResolver.query() to return a cursor when trying to query user audio files, but null is returned.")
      } catch (throwable: Throwable) {
         Result.failure(throwable)
      }
   }
}
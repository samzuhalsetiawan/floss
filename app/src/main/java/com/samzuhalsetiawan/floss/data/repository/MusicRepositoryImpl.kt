package com.samzuhalsetiawan.floss.data.repository

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository

class MusicRepositoryImpl(
   private val applicationContext: Context
): MusicRepository {

   override suspend fun getMusics(): List<Music> {
      val projection = arrayOf(
         MediaStore.Audio.AudioColumns._ID,
         MediaStore.Audio.AudioColumns.TITLE,
         MediaStore.Audio.AudioColumns.DISPLAY_NAME,
         MediaStore.Audio.AudioColumns.RELATIVE_PATH,
         MediaStore.Audio.AudioColumns.DATA,
      )
      val selection = "${MediaStore.Audio.Media.IS_MUSIC} != ?"
      val selectionArgs = arrayOf("0")
      return applicationContext.contentResolver.query(
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
         musics.also { mediaMetadataRetriever.release() }
      } ?: throw Exception("Expecting a cursor but null is returned.")
   }
}
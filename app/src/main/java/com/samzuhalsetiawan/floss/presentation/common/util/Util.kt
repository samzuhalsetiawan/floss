package com.samzuhalsetiawan.floss.presentation.common.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.core.net.toUri
import com.samzuhalsetiawan.floss.presentation.common.model.Music
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.Locale

private typealias MusicDTO = com.samzuhalsetiawan.floss.domain.model.Music

tailrec fun Context.getActivity(): ComponentActivity? = when (this) {
   is ComponentActivity -> this
   is ContextWrapper -> baseContext.getActivity()
   else -> null
}

fun formatMillisToTime(millis: Long): String {
   val duration = Duration.ofMillis(millis)
   val hours = duration.toHours()
   val minutes = duration.toMinutesPart()
   val seconds = duration.toSecondsPart()

   return if (hours > 0) {
      String.Companion.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
   } else {
      String.Companion.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
   }
}

fun ByteArray.asBitmap(): Bitmap? {
   return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun MusicDTO.toMusic(): Music {
   return Music(
      id = id,
      title = title,
      uri = uri.toUri(),
      displayName = displayName,
      albumArt = albumArt?.asBitmap()
   )
}

fun <T1, T2> MutableStateFlow<T1>.updateByCollectFromFlow(
   coroutineScope: CoroutineScope,
   flow: Flow<T2>,
   updateState: (T1, T2) -> T1
) {
   coroutineScope.launch {
      flow.collect {
         update { currentState ->
            updateState(currentState, it)
         }
      }
   }
}
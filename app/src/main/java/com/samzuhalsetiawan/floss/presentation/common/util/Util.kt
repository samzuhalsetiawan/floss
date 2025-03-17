package com.samzuhalsetiawan.floss.presentation.common.util

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import java.time.Duration
import java.util.Locale

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
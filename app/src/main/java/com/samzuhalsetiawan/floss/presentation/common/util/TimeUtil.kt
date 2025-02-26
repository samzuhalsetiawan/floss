package com.samzuhalsetiawan.floss.presentation.common.util

import java.time.Duration
import java.util.Locale

fun formatMillisToTime(millis: Long): String {
   val duration = Duration.ofMillis(millis)
   val hours = duration.toHours()
   val minutes = duration.toMinutesPart()
   val seconds = duration.toSecondsPart()

   return if (hours > 0) {
      String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
   } else {
      String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
   }
}
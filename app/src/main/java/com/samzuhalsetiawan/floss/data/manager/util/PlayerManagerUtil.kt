package com.samzuhalsetiawan.floss.data.manager.util

import androidx.media3.common.Player
import com.samzuhalsetiawan.floss.domain.manager.PlayerManager

fun PlayerManager.RepeatMode.toMedia3RepeatMode(): Int {
   return when (this) {
      PlayerManager.RepeatMode.NONE -> Player.REPEAT_MODE_OFF
      PlayerManager.RepeatMode.ONE -> Player.REPEAT_MODE_ONE
      PlayerManager.RepeatMode.ALL -> Player.REPEAT_MODE_ALL
   }
}

fun Int.toRepeatMode(): PlayerManager.RepeatMode {
   return when (this) {
      Player.REPEAT_MODE_OFF -> PlayerManager.RepeatMode.NONE
      Player.REPEAT_MODE_ONE -> PlayerManager.RepeatMode.ONE
      Player.REPEAT_MODE_ALL -> PlayerManager.RepeatMode.ALL
      else -> throw IllegalStateException("Unknown repeat mode")
   }
}
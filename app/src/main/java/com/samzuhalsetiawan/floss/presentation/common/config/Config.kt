package com.samzuhalsetiawan.floss.presentation.common.config

import android.os.Build

object Config {

   val readAudiFilesPermission =
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         android.Manifest.permission.READ_MEDIA_AUDIO
      } else {
         android.Manifest.permission.READ_EXTERNAL_STORAGE
      }

}
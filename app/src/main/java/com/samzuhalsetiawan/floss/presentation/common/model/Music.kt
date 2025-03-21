package com.samzuhalsetiawan.floss.presentation.common.model

import android.graphics.Bitmap
import android.net.Uri

data class Music(
   val id: String,
   val title: String,
   val uri: Uri,
   val displayName: String,
   val albumArt: Bitmap?
)
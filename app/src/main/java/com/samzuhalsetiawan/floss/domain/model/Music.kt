package com.samzuhalsetiawan.floss.domain.model

import android.net.Uri

data class Music(
   val id: String,
   val title: String,
   val uri: Uri,
   val displayName: String,
   val relativePath: String,
   val data: String,
)

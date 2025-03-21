package com.samzuhalsetiawan.floss.domain.model

data class Music(
   val id: String,
   val title: String,
   val uri: String,
   val displayName: String,
   val relativePath: String,
   val data: String,
   val albumArt: ByteArray?
) {
   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Music

      if (id != other.id) return false
      if (title != other.title) return false
      if (uri != other.uri) return false
      if (displayName != other.displayName) return false
      if (relativePath != other.relativePath) return false
      if (data != other.data) return false
      if (!albumArt.contentEquals(other.albumArt)) return false

      return true
   }

   override fun hashCode(): Int {
      var result = id.hashCode()
      result = 31 * result + title.hashCode()
      result = 31 * result + uri.hashCode()
      result = 31 * result + displayName.hashCode()
      result = 31 * result + relativePath.hashCode()
      result = 31 * result + data.hashCode()
      result = 31 * result + (albumArt?.contentHashCode() ?: 0)
      return result
   }
}

package com.samzuhalsetiawan.floss.domain

sealed interface Error

enum class GetAllMusicError: Error {
   NOT_ALLOWED_TO_READ_USER_MEDIA_AUDIO
}
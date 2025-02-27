package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import com.samzuhalsetiawan.floss.domain.model.Music

data class MusicListScreenState(
   val isLoading: Boolean = true,
   val musics: List<Music> = emptyList()
)

sealed class MusicListScreenEvent
package com.samzuhalsetiawan.floss.domain.repository

import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

   val musics: Flow<List<Music>>

   suspend fun reloadMusics()
}
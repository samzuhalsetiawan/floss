package com.samzuhalsetiawan.floss.domain.repository

import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MusicRepository {

   val musics: Flow<List<Music>>

   suspend fun reloadMusics()
}
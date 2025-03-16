package com.samzuhalsetiawan.floss.domain.repository

import com.samzuhalsetiawan.floss.domain.model.Music
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MusicRepository {
   suspend fun getAllMusics(): Result<List<Music>>
}
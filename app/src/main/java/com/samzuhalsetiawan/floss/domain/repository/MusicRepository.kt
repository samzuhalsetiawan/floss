package com.samzuhalsetiawan.floss.domain.repository

import com.samzuhalsetiawan.floss.domain.model.Music

interface MusicRepository {
   suspend fun getAllMusics(): List<Music>
}
package com.samzuhalsetiawan.floss.presentation.screen.musiclistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicListScreenVM(
   private val musicRepository: MusicRepository
): ViewModel() {

   private val _state = MutableStateFlow(MusicListScreenState())
   val state = _state.asStateFlow()

   fun onEvent(event: MusicListScreenEvent) {

   }

   init {
      getAllMusics()
   }

   private fun getAllMusics() {
      viewModelScope.launch(Dispatchers.IO) {
         _state.update { it.copy(isLoading = true) }
         try {
            val musics = musicRepository.getAllMusics()
            _state.update { it.copy(musics = musics) }
         } catch (th: Throwable) {
            throw th /* TODO: Handle error */
         } finally {
            _state.update { it.copy(isLoading = false) }
         }
      }
   }

}
package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.GetAllMusicError
import com.samzuhalsetiawan.floss.domain.Result
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetAllMusic(
   private val musicRepository: MusicRepository,
   private val permissionManager: PermissionManager
) {
   operator fun invoke(): Result<Flow<List<Music>>, GetAllMusicError> {
      val isReadUserMediaAudioAllowed = permissionManager.checkIfPermissionGranted(PermissionManager.Permission.READ_USER_MEDIA_AUDIO)
      if (!isReadUserMediaAudioAllowed) return Result.Failed(GetAllMusicError.NOT_ALLOWED_TO_READ_USER_MEDIA_AUDIO)
      val musics = musicRepository.musics
      return Result.Success(musics)
   }
}
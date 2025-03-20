package com.samzuhalsetiawan.floss.domain.usecase

import com.samzuhalsetiawan.floss.domain.GetAllMusicError
import com.samzuhalsetiawan.floss.domain.Result
import com.samzuhalsetiawan.floss.domain.manager.PermissionManager
import com.samzuhalsetiawan.floss.domain.model.Music
import com.samzuhalsetiawan.floss.domain.repository.MusicRepository

class GetAllMusic(
   private val musicRepository: MusicRepository,
   private val permissionManager: PermissionManager
) {
   suspend operator fun invoke(): Result<List<Music>, GetAllMusicError> {
      val isReadUserMediaAudioAllowed = permissionManager.checkIfPermissionGranted(PermissionManager.Permission.READ_USER_MEDIA_AUDIO)
      if (!isReadUserMediaAudioAllowed) return Result.Failed(GetAllMusicError.NOT_ALLOWED_TO_READ_USER_MEDIA_AUDIO)
      val musics = musicRepository.getMusics()
      return Result.Success(musics)
   }
}
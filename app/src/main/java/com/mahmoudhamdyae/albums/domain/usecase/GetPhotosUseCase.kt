package com.mahmoudhamdyae.albums.domain.usecase

import com.mahmoudhamdyae.albums.domain.model.Photos
import com.mahmoudhamdyae.albums.domain.repository.AlbumsRepository
import com.mahmoudhamdyae.albums.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: AlbumsRepository
) {

    suspend operator fun invoke(albumId: String): Result<Photos> {
        return try {
            Result.Success(repository.getPhotos(albumId))
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something wrong happened. Please try again.")
        }
    }
}
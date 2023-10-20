package com.mahmoudhamdyae.albums.domain.usecase

import com.mahmoudhamdyae.albums.domain.model.Albums
import com.mahmoudhamdyae.albums.domain.repository.AlbumsRepository
import com.mahmoudhamdyae.albums.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: AlbumsRepository
) {

    suspend operator fun invoke(userId: String): Result<Albums> {
        return try {
            Result.Success(repository.getAlbums(userId))
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something wrong happened. Please try again.")
        }
    }
}
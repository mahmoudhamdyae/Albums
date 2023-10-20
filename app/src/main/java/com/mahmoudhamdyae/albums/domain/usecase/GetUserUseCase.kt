package com.mahmoudhamdyae.albums.domain.usecase

import com.mahmoudhamdyae.albums.domain.model.User
import com.mahmoudhamdyae.albums.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import com.mahmoudhamdyae.albums.util.Result
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AlbumsRepository
) {
    suspend operator fun invoke(userId: String): Result<User> {
        return try {
            Result.Success(repository.getUser(userId))
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something wrong happened. Please try again.")
        }
    }
}
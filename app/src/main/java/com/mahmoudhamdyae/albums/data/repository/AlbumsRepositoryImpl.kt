package com.mahmoudhamdyae.albums.data.repository

import com.mahmoudhamdyae.albums.data.remote.ApiService
import com.mahmoudhamdyae.albums.domain.model.User
import com.mahmoudhamdyae.albums.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): AlbumsRepository {

    override suspend fun getUser(userId: String): User {
        return apiService.getUser(userId)
    }

    override suspend  fun getAlbums(userId: String) = apiService.getAlbums(userId)

    override suspend  fun getPhotos(albumId: String) = apiService.getPhotos(albumId)
}
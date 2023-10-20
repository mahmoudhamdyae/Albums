package com.mahmoudhamdyae.albums.domain.repository

import com.mahmoudhamdyae.albums.domain.model.Albums
import com.mahmoudhamdyae.albums.domain.model.Photos
import com.mahmoudhamdyae.albums.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    suspend fun getUser(userId: String): User
    suspend fun getAlbums(userId: String): Albums
    suspend fun getPhotos(albumId: String): Photos
}
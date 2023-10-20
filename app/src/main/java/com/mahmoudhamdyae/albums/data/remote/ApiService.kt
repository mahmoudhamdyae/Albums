package com.mahmoudhamdyae.albums.data.remote

import com.mahmoudhamdyae.albums.domain.model.Albums
import com.mahmoudhamdyae.albums.domain.model.Photos
import com.mahmoudhamdyae.albums.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User

    @GET("albums")
    suspend fun getAlbums(@Query("userId") userId: String): Albums

    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId: String): Photos
}
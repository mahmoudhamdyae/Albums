package com.mahmoudhamdyae.albums.di

import com.mahmoudhamdyae.albums.data.remote.ApiService
import com.mahmoudhamdyae.albums.data.repository.AlbumsRepositoryImpl
import com.mahmoudhamdyae.albums.domain.repository.AlbumsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(apiService: ApiService): AlbumsRepository {
        return AlbumsRepositoryImpl(apiService)
    }
}
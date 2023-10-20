package com.mahmoudhamdyae.albums.di

import com.mahmoudhamdyae.albums.domain.repository.AlbumsRepository
import com.mahmoudhamdyae.albums.domain.usecase.GetAlbumsUseCase
import com.mahmoudhamdyae.albums.domain.usecase.GetPhotosUseCase
import com.mahmoudhamdyae.albums.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUserUseCase(repository: AlbumsRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    fun provideAlbumsUseCase(repository: AlbumsRepository): GetAlbumsUseCase {
        return GetAlbumsUseCase(repository)
    }

    @Provides
    fun providePhotosUseCase(repository: AlbumsRepository): GetPhotosUseCase {
        return GetPhotosUseCase(repository)
    }
}
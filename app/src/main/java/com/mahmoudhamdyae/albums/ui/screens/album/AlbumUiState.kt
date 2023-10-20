package com.mahmoudhamdyae.albums.ui.screens.album

import com.mahmoudhamdyae.albums.domain.model.Photo

data class AlbumUiState(
    val photos: List<Photo> = listOf(),
    val clickedPhoto: Photo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
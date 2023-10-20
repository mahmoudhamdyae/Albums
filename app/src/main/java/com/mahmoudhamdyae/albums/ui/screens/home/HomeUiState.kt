package com.mahmoudhamdyae.albums.ui.screens.home

import com.mahmoudhamdyae.albums.domain.model.Album
import com.mahmoudhamdyae.albums.domain.model.User

data class HomeUiState(
    val user: User? = null,
    val albums: List<Album> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)
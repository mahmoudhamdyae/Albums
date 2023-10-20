package com.mahmoudhamdyae.albums.ui.screens.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.albums.domain.model.Photo
import com.mahmoudhamdyae.albums.domain.model.Photos
import com.mahmoudhamdyae.albums.domain.usecase.GetPhotosUseCase
import com.mahmoudhamdyae.albums.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    var uiState by  mutableStateOf(AlbumUiState())
        private set

    private lateinit var cachedPhotos: List<Photo>

    fun getPhotos(albumId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null,
            )
            when (val response = getPhotosUseCase(albumId)) {
                is Result.Success -> {
                    cachedPhotos = response.data
                    uiState = uiState.copy(
                        photos = response.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    cachedPhotos = listOf()
                    uiState = uiState.copy(
                        photos = listOf(),
                        isLoading = false,
                        error = response.message
                    )
                }
            }
        }
    }

    fun filterPhotos(searchText: String) {
        uiState = uiState.copy(
            photos = cachedPhotos.filter { it.title.contains(searchText) }
        )
    }

    fun clickPhoto(photo: Photo) {
        uiState = uiState.copy(clickedPhoto = photo)
    }
}
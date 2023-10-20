package com.mahmoudhamdyae.albums.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.albums.domain.usecase.GetAlbumsUseCase
import com.mahmoudhamdyae.albums.domain.usecase.GetUserUseCase
import com.mahmoudhamdyae.albums.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAlbumsUseCase: GetAlbumsUseCase
): ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null,
            )
            when (val response = getUserUseCase(Random.nextInt(1, 11).toString())) {
                is Result.Success -> {
                    uiState = uiState.copy(
                        user = response.data,
                        isLoading = false,
                        error = null
                    )
                    getAlbums(response.data.id.toString())
                }
                is Result.Error -> {
                    uiState = uiState.copy(
                        user = null,
                        isLoading = false,
                        error = response.message
                    )
                }
            }
        }
    }

    private fun getAlbums(userId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null,
            )
            when (val response = getAlbumsUseCase(userId)) {
                is Result.Success -> {
                    uiState = uiState.copy(
                        albums = response.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    uiState = uiState.copy(
                        albums = listOf(),
                        isLoading = false,
                        error = response.message
                    )
                }
            }
        }
    }
}
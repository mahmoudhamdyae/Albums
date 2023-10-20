package com.mahmoudhamdyae.albums.ui.screens.album

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore.Images
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mahmoudhamdyae.albums.R
import com.mahmoudhamdyae.albums.domain.model.Photo
import com.mahmoudhamdyae.albums.ui.composable.BasicToolBar
import com.mahmoudhamdyae.albums.ui.composable.ErrorMessage
import com.mahmoudhamdyae.albums.ui.composable.Loading
import com.mahmoudhamdyae.albums.ui.composable.Photo
import com.mahmoudhamdyae.albums.ui.navigation.NavigationDestination
import com.mahmoudhamdyae.albums.util.TestTags
import com.mahmoudhamdyae.albums.util.clickableNoRipple
import java.net.URL

object AlbumDestination: NavigationDestination {
    override val route: String = "album"
    override val titleRes: Int = R.string.album_screen_title
    const val albumIdArg = "albumId"
    const val albumTitleArg = "albumTitle"
    val routeWithArgs = "$route/{$albumIdArg}/{$albumTitleArg}"
    val arguments = listOf(
        navArgument(albumIdArg) { type = NavType.StringType },
        navArgument(albumTitleArg) { type = NavType.StringType }
    )
}

@Composable
fun AlbumScreen(
    albumId: String,
    albumTitle: String,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val uiState = viewModel.uiState
    LaunchedEffect(LocalContext.current) {
        viewModel.getPhotos(albumId)
    }

    var isShowingPhoto by rememberSaveable { mutableStateOf(false) }
    var isToolBarHidden by rememberSaveable { mutableStateOf(false) }

    BackHandler(
        enabled = isShowingPhoto
    ) {
        isShowingPhoto = false
    }

    BasicToolBar(
        title = if (isShowingPhoto) uiState.clickedPhoto?.title!! else albumTitle,
        canNavigateUp = true,
        navigateUp = {
            if (isShowingPhoto) {
                isShowingPhoto = false
            } else {
                navigateUp()
            }
        },
        isHidden = isToolBarHidden,
        isShareButtonVisible = isShowingPhoto,
        share = {
            shareImage(context, uiState.clickedPhoto)
        },
        modifier = modifier
    ) {
        if (uiState.error != null) {
            ErrorMessage()
        } else if (uiState.isLoading) {
            Loading()
        } else {
            AnimatedVisibility(visible = isShowingPhoto) {
                PhotoScreen(
                    photo = uiState.clickedPhoto!!,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickableNoRipple { isToolBarHidden = !isToolBarHidden }
                )
            }
            if (!isShowingPhoto) {
                AlbumScreenContent(
                    photos = uiState.photos,
                    onSearch = viewModel::filterPhotos,
                    clickPhoto = {
                        isShowingPhoto = !isShowingPhoto
                        viewModel.clickPhoto(it)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreenContent(
    photos: List<Photo>,
    onSearch: (String) -> Unit,
    clickPhoto: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        TextField(
            singleLine = true,
            value = searchText,
            onValueChange = { searchText = it; onSearch(it) },
            placeholder = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_content_description)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        PhotosList(
            photos = photos,
            clickPhoto = clickPhoto
        )
    }
}

@Composable
fun PhotosList(
    photos: List<Photo>,
    clickPhoto: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    if (photos.isEmpty()) {
        Text(
            text = stringResource(id = R.string.empty_text),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier.testTag(TestTags.ALBUMS_PHOTOS)
        ) {
            items(count = photos.size, key = { photos[it].id }) {
                PhotosListItem(
                    photo = photos[it],
                    modifier = modifier.clickable { clickPhoto(photos[it]) }
                )
            }
        }
    }
}

@Composable
fun PhotosListItem(
    photo: Photo,
    modifier: Modifier = Modifier
) {
    Photo(
        photoUri = photo.thumbnailUrl.toUri(),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PhotosListPreview() {
    PhotosList(
        photos = listOf(
            Photo(
                albumId = 1,
                id = 1,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                url = "https://via.placeholder.com/600/92c952",
                thumbnailUrl = "https://via.placeholder.com/150/92c952"
            ),
            Photo(
                albumId = 1,
                id = 2,
                title = "reprehenderit est deserunt velit ipsam",
                url = "https://via.placeholder.com/600/771796",
                thumbnailUrl = "https://via.placeholder.com/150/771796"
            ),
        ),
        clickPhoto = { }
    )
}

private fun shareImage(context: Context, photo: Photo?) {
    Thread {
        val url = URL(photo?.thumbnailUrl)
        val connection = url.openConnection()
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream

        val imgBitmap = BitmapFactory.decodeStream(input)
        val imgBitmapPath = Images.Media.insertImage(
            context.contentResolver,
            imgBitmap,
            photo?.title,
            null
        )
        val imgBitmapUri = Uri.parse(imgBitmapPath)

        // share Intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Intent.EXTRA_STREAM, imgBitmapUri)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_TEXT, photo?.title)
        }
        // Open the chooser dialog box
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_with)))
    }.start()
}
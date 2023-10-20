package com.mahmoudhamdyae.albums.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudhamdyae.albums.R
import com.mahmoudhamdyae.albums.domain.model.Address
import com.mahmoudhamdyae.albums.domain.model.Album
import com.mahmoudhamdyae.albums.domain.model.Company
import com.mahmoudhamdyae.albums.domain.model.Geo
import com.mahmoudhamdyae.albums.domain.model.User
import com.mahmoudhamdyae.albums.ui.composable.BasicToolBar
import com.mahmoudhamdyae.albums.ui.composable.ErrorMessage
import com.mahmoudhamdyae.albums.ui.composable.Loading
import com.mahmoudhamdyae.albums.ui.navigation.NavigationDestination
import com.mahmoudhamdyae.albums.util.TestTags

object HomeDestination: NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home_screen_title
}

@Composable
fun HomeScreen(
    navigateToPhotosScreen: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    BasicToolBar(
        title = stringResource(id = HomeDestination.titleRes),
        modifier = modifier
    ) {
        if (uiState.error != null) {
            ErrorMessage()
        } else if (uiState.isLoading) {
            Loading()
        } else {
            uiState.user?.let { user ->
                HomeScreenContent(
                    user = user,
                    albums = uiState.albums,
                    navigateToPhotosScreen = navigateToPhotosScreen
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    user: User,
    albums: List<Album>,
    navigateToPhotosScreen: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        UserContent(user = user)
        AlbumsContent(albums = albums, navigateToPhotosScreen = navigateToPhotosScreen)
    }
}

@Composable
fun UserContent(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.testTag(TestTags.USER_CONTENT)
    ) {
        Text(
            text = user.name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${user.address.street}, ${user.address.suite}, ${user.address.city}, ${user.address.zipcode}"
        )
    }
}

@Composable
fun AlbumsContent(
    albums: List<Album>,
    navigateToPhotosScreen: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.testTag(TestTags.ALBUMS_CONTENT)
    ) {
        Text(
            text = stringResource(id = R.string.albums),
            fontWeight = FontWeight.Bold
        )
        AlbumsList(albums = albums, navigateToPhotosScreen = navigateToPhotosScreen)
    }
}

@Composable
fun AlbumsList(
    albums: List<Album>,
    navigateToPhotosScreen: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.testTag(TestTags.ALBUM_TITLE)) {
        items(count = albums.size, key = { albums[it].id }) {
            AlbumsListItem(
                album = albums[it],
                modifier = Modifier
                    .clickable {
                        navigateToPhotosScreen(albums[it].id.toString(), albums[it].title)
                    }
            )
        }
    }
}

@Composable
fun AlbumsListItem(
    album: Album,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
        Text(text = album.title)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        user = User(
            address = Address("Gwenborough", Geo("-37.3159", "81.1496"), "Kulas Light", "Apt. 556", "92998-3874"),
            company = Company("harness real-time e-markets", "Multi-layered client-server neural-net", "Romaguera-Crona"),
            email = "Sincere@april.biz",
            id = 1,
            name = "Leanne Graham",
            phone = "1-770-736-8031 x56442",
            username = "Bret",
            website = "hildegard.org"),
        albums = listOf(
            Album(
                id = 1,
                title = "quidem molestiae enim",
                userId = 1
            ),
            Album(
                id = 2,
                title = "sunt qui excepturi placeat culpa",
                userId = 1
            )
        ),
        navigateToPhotosScreen = { _, _ -> }
    )
}
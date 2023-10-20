package com.mahmoudhamdyae.albums.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mahmoudhamdyae.albums.ui.screens.home.HomeDestination
import com.mahmoudhamdyae.albums.ui.screens.home.HomeScreen
import com.mahmoudhamdyae.albums.ui.screens.album.AlbumDestination
import com.mahmoudhamdyae.albums.ui.screens.album.AlbumScreen

@Composable
fun AlbumsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        val openScreen: (String) -> Unit = { route ->
            navController.navigate(route) { launchSingleTop = true }
        }
        val navigateUp: () -> Unit = { navController.popBackStack() }

        composable(route = HomeDestination.route) {
            HomeScreen(navigateToPhotosScreen = { id, title ->
                openScreen("${AlbumDestination.route}/$id/$title")
            })
        }

        composable(
            route = AlbumDestination.routeWithArgs,
            arguments = AlbumDestination.arguments
        ) { navBackStack ->
            val albumId = navBackStack.arguments?.getString(AlbumDestination.albumIdArg)!!
            val albumTitle = navBackStack.arguments?.getString(AlbumDestination.albumTitleArg)!!
            AlbumScreen(
                albumId = albumId,
                albumTitle = albumTitle,
                navigateUp = navigateUp
            )
        }
    }
}
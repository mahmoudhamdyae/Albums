package com.mahmoudhamdyae.albums.ui.composable

import android.net.Uri
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mahmoudhamdyae.albums.R

@Composable
fun Photo(
    modifier: Modifier = Modifier,
    photoUri: Uri? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(data = photoUri)
            .build(),
        placeholder = painterResource(R.drawable.loading_img),
        error = painterResource(R.drawable.default_image),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}
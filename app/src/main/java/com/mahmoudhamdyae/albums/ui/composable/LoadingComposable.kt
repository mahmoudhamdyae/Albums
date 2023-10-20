package com.mahmoudhamdyae.albums.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.mahmoudhamdyae.albums.util.TestTags

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().testTag(TestTags.LOADING)) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
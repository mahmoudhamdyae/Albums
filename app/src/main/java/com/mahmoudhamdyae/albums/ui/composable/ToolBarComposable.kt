package com.mahmoudhamdyae.albums.ui.composable

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.mahmoudhamdyae.albums.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicToolBar(
    title: String,
    modifier: Modifier = Modifier,
    canNavigateUp: Boolean = false,
    navigateUp: () -> Unit = { },
    isHidden: Boolean = false,
    isShareButtonVisible: Boolean = false,
    share: () -> Unit = { },
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        AnimatedVisibility(visible = !isHidden) {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )},
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    if (canNavigateUp) {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_content_description)
                            )
                        }
                    }
                },
                actions = {
                    if (isShareButtonVisible) {
                        IconButton(onClick = share) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = stringResource(id = R.string.share_content_description)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                scrollBehavior = null,
            )
        }
        content()
    }
}
package com.justparokq.homeftp.shared.ftp.presentation.composables.gallery_content.element_types

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject


@Composable
internal fun ImageGalleryElement(file: FileSystemObject.File.Image, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(file.getPreviewUrl())
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        loading = {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        },
        error = {
            // todo if 401 retry
            Icon(
                Icons.Default.BrokenImage,
                contentDescription = "Broken Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp)
            )
        },
        modifier = modifier,
    )
}
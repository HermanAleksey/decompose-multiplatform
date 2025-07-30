package com.justparokq.homeftp.shared.ftp.presentation.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.Path
import kotlinx.coroutines.flow.distinctUntilChanged

private const val FILES_IN_LINE_DEFAULT_NUMBER = 3

@Composable
internal fun FtpScreen(
    path: Path,
    fsObjects: List<FileSystemObject>,
    onPathPartClicked: (Path) -> Unit,
    onFSObjectClicked: (FileSystemObject) -> Unit,
    onNavigateBackClicked: () -> Unit,
    onEndOfPageReached: () -> Unit,
    isLoadingPagination: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        CurrentPathLine(
            path = path,
            onPathPartClicked = onPathPartClicked,
            onNavigateBackClicked = onNavigateBackClicked,
            modifier = Modifier.fillMaxWidth(),
        )
        GalleryView(
            fileSystemObjects = fsObjects,
            onFSObjectClicked = onFSObjectClicked,
            modifier = Modifier.fillMaxSize(),
            isLoadingPagination = isLoadingPagination,
            onEndOfPageReached = onEndOfPageReached
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}

private const val LOADING_VISIBILITY_OFFSET = 9

@Composable
internal fun GalleryView(
    fileSystemObjects: List<FileSystemObject>,
    onFSObjectClicked: (FileSystemObject) -> Unit,
    isLoadingPagination: Boolean,
    onEndOfPageReached: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    println("isLoadingPagination:$isLoadingPagination")

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            lastVisible >= total - LOADING_VISIBILITY_OFFSET
        }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) {
                    onEndOfPageReached()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        fileSystemObjects.chunked(FILES_IN_LINE_DEFAULT_NUMBER).forEach { rowOfFiles ->
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp),
                ) {
                    repeat(FILES_IN_LINE_DEFAULT_NUMBER) { index ->
                        Spacer(modifier = Modifier.width(6.dp))
                        if (index < rowOfFiles.size) {
                            ClickableCard(rowOfFiles, index, onFSObjectClicked)
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        if (isLoadingPagination) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
//                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun RowScope.ClickableCard(
    rowOfFiles: List<FileSystemObject>,
    index: Int,
    onFSObjectClicked: (FileSystemObject) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animatedElevation by animateDpAsState(
        targetValue = if (isPressed) (-3).dp else 10.dp,
        label = "cardElevation"
    )

    Card(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
        onClick = { onFSObjectClicked(rowOfFiles[index]) },
        interactionSource = interactionSource,
        elevation = CardDefaults.cardElevation(defaultElevation = animatedElevation)
    ) {
        FileElement(
            file = rowOfFiles[index],
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun FileElement(
    file: FileSystemObject,
    modifier: Modifier = Modifier,
) {
    when (file) {
        is FileSystemObject.Directory -> {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Folder,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Folder",
                    modifier = Modifier.fillMaxSize(0.75f)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
        }

        is FileSystemObject.File.Image -> {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(file.getPreviewUrl())
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
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
                }
            )
        }

        FileSystemObject.File.Unknown, is FileSystemObject.File.Video -> {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = "Unknown file",
                    modifier = Modifier.fillMaxSize(0.75f),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Unknown",
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
        }
    }
}
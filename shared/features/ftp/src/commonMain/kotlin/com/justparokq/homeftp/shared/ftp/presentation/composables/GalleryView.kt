package com.justparokq.homeftp.shared.ftp.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponentIntent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerScreenModel
import com.justparokq.homeftp.shared.ftp.api.OnEndOfPageReached
import com.justparokq.homeftp.shared.ftp.api.OnFileSystemObjectClicked
import com.justparokq.homeftp.shared.ftp.model.FILES_IN_LINE_DEFAULT_NUMBER
import com.justparokq.homeftp.shared.ftp.presentation.TOOLBAR_HEIGHT
import com.justparokq.homeftp.shared.ftp.presentation.composables.gallery_content.GalleryClickableCard
import com.justparokq.homeftp.shared.ftp.presentation.composables.gallery_content.GalleryPaginationLoader
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged

private const val LOADING_VISIBILITY_OFFSET = 9

@Composable
internal fun GalleryView(
    state: FtpExplorerScreenModel,
    processIntent: (FtpExplorerComponentIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            lastVisible >= total - LOADING_VISIBILITY_OFFSET
        }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) {
                    processIntent(OnEndOfPageReached)
                }
            }
    }

    var showPaginationError by remember { mutableStateOf(false) }
    println("showPaginationError: $showPaginationError, ${state.paginationState.error}")
    LaunchedEffect(state.paginationState.error) {
        println("launched: $showPaginationError , ${state.paginationState.error}")
        if (state.paginationState.error != null) {
            showPaginationError = true
            delay(2000)
            showPaginationError = false
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(TOOLBAR_HEIGHT.dp)) }
        state.fsObjects.chunked(FILES_IN_LINE_DEFAULT_NUMBER).forEach { rowOfFiles ->
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
                            GalleryClickableCard(
                                rowOfFiles = rowOfFiles,
                                index = index,
                                onFSObjectClicked = { processIntent(OnFileSystemObjectClicked(it)) },
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        if (state.paginationState.isLoadingNextPage) {
            item {
                GalleryPaginationLoader()
            }
        }

        // pagination error
        if (showPaginationError) {
            item {
                AnimatedVisibility(
                    visible = showPaginationError,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    PaginationErrorLabel(Modifier.fillMaxWidth())
                }
            }
        }

        // to let user scroll a bit lower
        item { Spacer(modifier = Modifier.height(60.dp)) }
    }
}

@Composable
internal fun PaginationErrorLabel(
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Couldn't load, retry later", color = MaterialTheme.colorScheme.error)
    }
}
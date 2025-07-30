package com.justparokq.homeftp.shared.ftp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerScreenModel
import com.justparokq.homeftp.shared.ftp.api.OnDirectoryClicked
import com.justparokq.homeftp.shared.ftp.api.OnEndOfPageReached
import com.justparokq.homeftp.shared.ftp.api.OnFileSystemObjectClicked
import com.justparokq.homeftp.shared.ftp.api.OnNavigateBackClicked
import com.justparokq.homeftp.shared.ftp.presentation.composables.FtpScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InternalFtpContent(
    component: FtpExplorerComponent,
) {
    val stateSubscription = component.state.subscribeAsState()
    val state = stateSubscription.value as? FtpExplorerScreenModel ?: return

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { /*todo*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Select file to upload"
                )
            }
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .background(MaterialTheme.colorScheme.background)
        ) {
            FtpScreen(
                path = state.currentPath,
                fsObjects = state.fsObjects,
                onPathPartClicked = { component.processIntent(OnDirectoryClicked(it)) },
                onFSObjectClicked = { component.processIntent(OnFileSystemObjectClicked(it)) },
                onNavigateBackClicked = { component.processIntent(OnNavigateBackClicked) },
                onEndOfPageReached = { component.processIntent(OnEndOfPageReached) },
                isLoadingPagination = state.paginationState.isLoadingNextPage,
                modifier = Modifier
            )
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                // disable clicks
                                while (true) {
                                    awaitPointerEvent()
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }
    }
}
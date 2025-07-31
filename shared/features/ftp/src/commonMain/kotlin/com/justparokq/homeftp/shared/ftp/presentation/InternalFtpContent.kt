package com.justparokq.homeftp.shared.ftp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerScreenModel
import com.justparokq.homeftp.shared.ftp.presentation.composables.CurrentPathLine
import com.justparokq.homeftp.shared.ftp.presentation.composables.GalleryContentError
import com.justparokq.homeftp.shared.ftp.presentation.composables.GalleryView
import com.justparokq.homeftp.shared.ftp.presentation.composables.LoadingContentState

const val TOOLBAR_HEIGHT = 72

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InternalFtpContent(
    component: FtpExplorerComponent,
) {
    val stateSubscription = component.state.subscribeAsState()
    val state = stateSubscription.value as? FtpExplorerScreenModel ?: return
    println("State: $state")

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            FloatingActionButton(onClick = { /*todo*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Select file to upload"
                )
            }
        }
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
        ) {
            if (state.isLoading) {
                LoadingContentState(modifier = Modifier.fillMaxSize())
            } else {
                if (state.error != null) {
                    GalleryContentError(
                        processIntent = { component.processIntent(it) },
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    GalleryView(
                        state = state,
                        processIntent = { component.processIntent(it) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

        CurrentPathLine(
            path = state.currentPath,
            processIntent = { component.processIntent(it) },
            modifier = Modifier.height(TOOLBAR_HEIGHT.dp)
        )
    }
}
package com.justparokq.homeftp.features.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.features.main.composables.FileSystemHierarchy
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.presentation.FtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.presentation.PreviewFtpExplorerComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(
    component: FtpExplorerComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.subscribeAsState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Client", style = MaterialTheme.typography.headlineMedium) },
            )
        },
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings)
                .background(MaterialTheme.colorScheme.background)
        ) {
            FileSystemHierarchy(
                fileHierarchy = state.value.fileTree,
                onFileClicked = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    MainContent(PreviewFtpExplorerComponent)
}

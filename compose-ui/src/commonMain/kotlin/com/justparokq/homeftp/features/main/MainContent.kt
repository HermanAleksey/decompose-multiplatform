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
import com.justparokq.homeftp.features.main.composables.FileSystemHierarchy
import com.justparokq.homeftp.features.main.model.FileSystemObject
import com.justparokq.homeftp.shared.main.MainComponent
import com.justparokq.homeftp.shared.main.PreviewMainComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
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
            val listDirectory = FileSystemObject.Directory(
                "/src",
                content = listOf(
                    FileSystemObject.Directory(
                        name = "/directory one",
                        content = listOf(
                            FileSystemObject.File(
                                name = "file 1.txt",
                                content = Unit
                            )
                        ),
                    ),
                    FileSystemObject.Directory(
                        name = "/directory two",
                        content = listOf(
                            FileSystemObject.File(
                                name = "file 2-1.txt",
                                content = Unit
                            ),
                            FileSystemObject.File(
                                name = "file 2-2.txt",
                                content = Unit
                            )
                        ),
                    ),
                    FileSystemObject.File(
                        name = "text3.txt",
                        content = Unit
                    )
                )
            )
            FileSystemHierarchy(
                fileHierarchy = listDirectory,
                onFileClicked = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    MainContent(PreviewMainComponent)
}

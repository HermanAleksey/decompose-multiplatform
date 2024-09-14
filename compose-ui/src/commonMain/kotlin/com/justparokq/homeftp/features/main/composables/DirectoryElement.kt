package com.justparokq.homeftp.features.main.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.theme.AppTheme


@Composable
fun DirectoryElement(
    directory: FileSystemObject.Directory,
    onFileClicked: (FileSystemObject.File) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showChildren by remember { mutableStateOf(true) }
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.clickable { showChildren = showChildren.not() },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                imageVector = Icons.Filled.Folder,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = directory.name,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        AnimatedVisibility(
            visible = showChildren
        ) {
            Column {
                directory.content?.forEach { fileSystemObject ->
                    FileSystemHierarchy(
                        fileHierarchy = fileSystemObject,
                        onFileClicked = onFileClicked,
                        modifier = modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DirectoryElementPreview() {
    AppTheme {
        DirectoryElement(
            directory = FileSystemObject.Directory(
                "src",
                content = listOf(
                    FileSystemObject.Directory(
                        name = "justparokq",
                        content = null,
                    ),
                    FileSystemObject.File(
                        name = "text.txt",
                        content = Unit
                    )
                )
            ),
            onFileClicked = {}
        )
    }
}
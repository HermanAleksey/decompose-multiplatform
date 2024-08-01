package com.justparokq.homeftp.features.main.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.justparokq.homeftp.features.main.model.FileSystemObject
import com.justparokq.homeftp.theme.AppTheme

@Composable
fun FileSystemHierarchy(
    fileHierarchy: FileSystemObject,
    onFileClicked: (FileSystemObject.File) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (fileHierarchy) {
            is FileSystemObject.Directory -> {
                DirectoryElement(fileHierarchy, onFileClicked)
            }

            is FileSystemObject.File -> {
                FileElement(
                    fileHierarchy,
                    onFileClicked
                )
            }
        }
    }
}

@Preview
@Composable
private fun DirectoriesPreview() {
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
    AppTheme {
        FileSystemHierarchy(
            fileHierarchy = listDirectory,
            onFileClicked = {}
        )
    }
}
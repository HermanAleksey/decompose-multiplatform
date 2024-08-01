package com.justparokq.homeftp.features.main.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.justparokq.homeftp.features.main.model.FileSystemObject
import com.justparokq.homeftp.theme.AppTheme

@Composable
fun FileElement(
    file: FileSystemObject.File,
    onFileClicked: (FileSystemObject.File) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = file.name,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.clickable {
            onFileClicked(file)
        }
    )
}

@Preview
@Composable
private fun FileElementPreview() {
    AppTheme {
        FileElement(
            file = FileSystemObject.File(
                name = "hello",
                content = Unit
            ),
            onFileClicked = {}
        )
    }
}
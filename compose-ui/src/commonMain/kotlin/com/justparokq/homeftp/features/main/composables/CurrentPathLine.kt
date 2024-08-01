package com.justparokq.homeftp.features.main.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.theme.AppTheme

@Composable
fun CurrentPathLine(
    path: List<String>,
    navigateToPath: (list: List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(8.dp)) {
            path.forEachIndexed { index, str ->
                Text(
                    text = str + "/",
                    modifier = Modifier.padding(end = 3.dp)
                        .clickable {
                            // should navigate to selected sub-path
                            navigateToPath(path.subList(0, index))
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DirectoriesPreview() {
    AppTheme {
        val path = listOf("hi", "nagga", "yo_gay")
        CurrentPathLine(path, {})
    }
}
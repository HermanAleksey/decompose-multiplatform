package com.justparokq.homeftp.shared.ftp.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponentIntent
import com.justparokq.homeftp.shared.ftp.api.OnDirectoryClicked
import com.justparokq.homeftp.shared.ftp.api.OnNavigateBackClicked
import com.justparokq.homeftp.shared.ftp.model.Path
import com.justparokq.homeftp.theme.LocalElevation
import kotlinx.coroutines.delay

@Composable
internal fun CurrentPathLine(
    path: Path,
    processIntent: (FtpExplorerComponentIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(path.parts.size) {
        if (path.parts.isNotEmpty()) {
            // waiting for layout a bit
            delay(100)
            listState.animateScrollToItem(path.parts.lastIndex)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .shadow(
                elevation = LocalElevation.current.medium,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        IconButton(onClick = { processIntent(OnNavigateBackClicked) }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        LazyRow(
            state = listState,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        ) {
            val partsToDisplay = listOf("Root") + path.parts
            itemsIndexed(partsToDisplay) { index, str ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = str,
                        modifier = Modifier
                            // -1 because we added 'root' label , so indexes
                            .clickable { processIntent(OnDirectoryClicked(path.resolveTo(index - 1))) }
                            .padding(horizontal = 4.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    if (index < partsToDisplay.lastIndex) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
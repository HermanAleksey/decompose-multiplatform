package com.justparokq.homeftp.shared.ftp.presentation.composables.gallery_content

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.presentation.composables.gallery_content.element_types.DirectoryGalleryElement
import com.justparokq.homeftp.shared.ftp.presentation.composables.gallery_content.element_types.ImageGalleryElement
import com.justparokq.homeftp.shared.ftp.presentation.composables.gallery_content.element_types.UnknownGalleryElement
import com.justparokq.homeftp.theme.LocalElevation

@Composable
internal fun RowScope.GalleryClickableCard(
    rowOfFiles: List<FileSystemObject>,
    index: Int,
    onFSObjectClicked: (FileSystemObject) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LocalElevation
    val animatedElevation by animateDpAsState(
        targetValue = if (isPressed) LocalElevation.current.none else LocalElevation.current.small,
        label = "cardElevation"
    )

    Card(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
        onClick = { onFSObjectClicked(rowOfFiles[index]) },
        interactionSource = interactionSource,
        elevation = CardDefaults.cardElevation(defaultElevation = animatedElevation)
    ) {
        when (val file = rowOfFiles[index]) {
            is FileSystemObject.Directory -> {
                DirectoryGalleryElement(
                    file = file,
                    modifier = Modifier.padding(8.dp)
                        .fillMaxSize()
                )
            }

            is FileSystemObject.File.Image -> {
                ImageGalleryElement(
                    file = file,
                    modifier = Modifier.fillMaxSize()
                )
            }

            FileSystemObject.File.Unknown, is FileSystemObject.File.Video -> {
                UnknownGalleryElement(
                    modifier = Modifier.padding(8.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}
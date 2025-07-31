package com.justparokq.homeftp.shared.ftp.api

import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.LocalFile
import com.justparokq.homeftp.shared.ftp.model.PaginationState
import com.justparokq.homeftp.shared.ftp.model.Path
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentIntent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentState
import io.github.vinceglb.filekit.core.PlatformFile

interface FtpExplorerComponent :
    BaseComponent<FtpExplorerComponentState, FtpExplorerComponentIntent>


sealed interface FtpExplorerComponentState : BaseComponentState

internal data class FtpExplorerScreenModel(
    val isLoading: Boolean = true,
    val currentPath: Path = Path.Root,
    val fsObjects: List<FileSystemObject> = emptyList(),
    val paginationState: PaginationState = PaginationState(),
    val error: String? = null,
) : FtpExplorerComponentState

sealed interface FtpExplorerComponentIntent : BaseComponentIntent

internal data object OnScreenOpened : FtpExplorerComponentIntent

/**
 * Navigates to directory by path
 */
internal data class OnDirectoryClicked(val dirPath: Path) : FtpExplorerComponentIntent

/**
 * Open fsObject
 * If file is Directory - navigates to it
 * If file is File - opens it (photo or video or smt else)
 * */
internal data class OnFileSystemObjectClicked(val fsObject: FileSystemObject) :
    FtpExplorerComponentIntent

/**
 * Not for MVP
 * Apply sorting to all files in MainView (currently opened directory)
 * This setting should persist between directories?
 * */
internal data object OnSortingApplyClicked : FtpExplorerComponentIntent

/**
 * Floating button meant to open file picker
 * Selected file(s) are uploaded to server to the current folder
 * */
internal data class OnFloatingButtonClicked(
    val files: List<LocalFile>
) : FtpExplorerComponentIntent

/**
 * Invoke when user selected one of few files from gallery
 * */
internal data class OnFilesPicked(val files: List<PlatformFile>) : FtpExplorerComponentIntent

internal data object OnNavigateBackClicked : FtpExplorerComponentIntent

internal data object OnEndOfPageReached : FtpExplorerComponentIntent

internal data object OnRefreshPulled : FtpExplorerComponentIntent

internal data object OnRetryButtonClicked : FtpExplorerComponentIntent

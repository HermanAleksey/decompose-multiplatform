package com.justparokq.homeftp.shared.ftp.model

// todo rename feature screen
data class FtpExplorerScreenModel(
    val currentPath: String = "",
    val fileTree: FileSystemObject.Directory = FileSystemObject.Directory("", null),
    val isLoading: Boolean = true,
    val showHierarchyView: Boolean = false,
)
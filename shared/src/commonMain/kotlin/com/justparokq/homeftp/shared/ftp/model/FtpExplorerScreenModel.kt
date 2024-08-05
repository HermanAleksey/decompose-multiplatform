package com.justparokq.homeftp.shared.ftp.model

// todo rename feature screen
data class FtpExplorerScreenModel(
    val currentPath: String,
    val fileTree: FileSystemObject.Directory,
    val isLoading: Boolean,
    val showHierarchyView: Boolean,
)
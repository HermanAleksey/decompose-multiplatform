package com.justparokq.homeftp.shared.ftp.presentation

import com.justparokq.homeftp.shared.ftp.model.FileSystemObject

object PreviewFtpExplorerComponent : FtpExplorerComponent {
    override fun onDirectoryClicked(dirPath: List<String>) {}

    override fun onFileClicked(file: FileSystemObject.File) {}

    override fun onToggleHierarchyView() {}
}

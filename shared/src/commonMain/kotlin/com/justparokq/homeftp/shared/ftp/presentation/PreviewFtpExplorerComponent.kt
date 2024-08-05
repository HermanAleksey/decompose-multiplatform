package com.justparokq.homeftp.shared.ftp.presentation

object PreviewFtpExplorerComponent : FtpExplorerComponent {
    override fun onDirectoryClicked(dirPath: List<String>) {}

    override fun onFileClicked(file: FileSystemObject.File) {}

    override fun onToggleHierarchyView() {}
}

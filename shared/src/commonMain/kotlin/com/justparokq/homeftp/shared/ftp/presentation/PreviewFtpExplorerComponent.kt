package com.justparokq.homeftp.shared.ftp.presentation

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.FtpExplorerScreenModel

object PreviewFtpExplorerComponent : FtpExplorerComponent {
    override val state: Value<FtpExplorerScreenModel>
        get() = TODO("Not yet implemented")

    override fun onDirectoryClicked(dirPath: List<String>) {}

    override fun onFileClicked(file: FileSystemObject.File) {}

    override fun onToggleHierarchyView() {}
}

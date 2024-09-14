package com.justparokq.homeftp.shared.ftp.presentation

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.FtpExplorerScreenModel

interface FtpExplorerComponent {

    val state: Value<FtpExplorerScreenModel>

    /**
     * Navigates to directory by path
     */
    fun onDirectoryClicked(dirPath: List<String>)

    /**
     * Open file
     * If file is Directory - navigates to it
     * If file is File - opens it (photo or video or smt else)
     * */
    fun onFileClicked(file: FileSystemObject.File)

    /**
     * Closes hierarchyView if it was open and otherwise
     * */
    fun onToggleHierarchyView()

    /**
     * Not for MVP
     * Apply sorting to all files in MainView (currently opened directory)
     * This setting should persist between directories?
     * */
    fun onSortingApplyClicked() {}
}

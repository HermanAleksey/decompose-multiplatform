package com.justparokq.homeftp.shared.ftp.presentation

import com.justparokq.homeftp.shared.ftp.model.FileSystemObject

interface FtpExplorerComponent {

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

package com.justparokq.homeftp.shared.main


// todo remove from compose-ui module
sealed interface FileSystemObject {

    data class Directory(
        val name: String,
        val content: List<FileSystemObject>?,
    ) : FileSystemObject

    data class File(
        val name: String,
        val content: Any, // todo update
    ) : FileSystemObject
}


// todo rename feature screen
data class FtpClientScreenModel(
    val currentPath: String,
    val fileTree: FileSystemObject.Directory,
    val isLoading: Boolean,
    val showHierarchyView: Boolean,
)

interface MainComponent {

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

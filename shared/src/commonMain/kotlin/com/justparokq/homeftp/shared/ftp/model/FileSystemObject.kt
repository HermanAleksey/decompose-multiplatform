package com.justparokq.homeftp.shared.ftp.model

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

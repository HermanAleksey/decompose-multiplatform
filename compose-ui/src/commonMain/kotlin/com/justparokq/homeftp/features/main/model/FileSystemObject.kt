package com.justparokq.homeftp.features.main.model

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

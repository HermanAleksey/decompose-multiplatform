package com.justparokq.ftp.utils

internal class PathProcessor(
    private val rootPath: String = PathConstants.DEFAULT_ROOT_STORAGE_PATH,
) {

    fun getRootDirectoryPath(): String = rootPath

    fun getPreviewRootDirectoryPath(): String = rootPath + "previews/"
}
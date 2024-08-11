package com.justparokq.ftp.utils

import java.io.File

private const val DEFAULT_ROOT_STORAGE_PATH = "./testStorageDirectory/"

internal class FileSystemCommunicator(
    private val rootPath: String = DEFAULT_ROOT_STORAGE_PATH,
) {

    fun getDirectoryContent(path: String): List<File>? {
        val directory = File(rootPath + path)
        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            if (files != null) {
                return files.toList()
            }
        } else {
            println("Указанный путь не является директорией или не существует.")
        }
        return null
    }

    fun getFile(path: String): File? {
        return File(rootPath + path).takeIf { it.isFile }
    }
}
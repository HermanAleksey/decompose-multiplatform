package com.justparokq.ftp.utils

import java.io.File

internal class FileSystemCommunicator(
    private val pathProcessor: PathProcessor,
) {

    fun getDirectoryContent(path: String): List<File>? {
        val directory = File(pathProcessor.getRootDirectoryPath() + path)
        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            if (files != null) {
                return files.toList()
            }
        } else {
            println("The specified path is not a directory or does not exist.")
        }
        return null
    }

    fun getFile(path: String): File? {
        return File(pathProcessor.getRootDirectoryPath() + path).takeIf { it.isFile }
    }
}
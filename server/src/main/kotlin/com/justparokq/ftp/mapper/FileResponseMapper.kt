package com.justparokq.ftp.mapper

import com.justparokq.homeftp.models.ftp.FileResponse
import java.io.File

internal class FileResponseMapper(
    private val rootPath: String,
) {

    fun map(files: List<File>): List<FileResponse> {
        return files.map { file ->
            FileResponse(
                uri = file.path.removePrefix(rootPath),
                name = file.name,
                isDirectory = file.isDirectory,
            )
        }
    }
}
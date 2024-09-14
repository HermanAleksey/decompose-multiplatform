package com.justparokq.homeftp.shared.ftp.data.mapper

import com.justparokq.homeftp.models.ftp.FileResponse
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject

interface FileSystemObjectMapper {

    fun map(fileResponse: FileResponse): FileSystemObject
}

internal class FileSystemObjectMapperImpl : FileSystemObjectMapper {

    override fun map(fileResponse: FileResponse): FileSystemObject {
        //todo how to do it better?
        return if (fileResponse.isDirectory)
            FileSystemObject.Directory(fileResponse.name, content = null)
        else FileSystemObject.File(fileResponse.name, content = Unit)
    }
}
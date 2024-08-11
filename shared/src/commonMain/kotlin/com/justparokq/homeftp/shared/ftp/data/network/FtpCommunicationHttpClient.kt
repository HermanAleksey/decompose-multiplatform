package com.justparokq.homeftp.shared.ftp.data.network

import com.justparokq.homeftp.models.ftp.FileResponse

val file = FileResponse(
    uri = "src/lesha/hello",
    name = "text.txt",
    isDirectory = false,
)

interface FtpCommunicationHttpClient {

    fun getDirectoryContent(uri: String): FileResponse
}
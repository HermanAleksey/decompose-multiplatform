package com.justparokq.routing.authorized

import FtpDependencies
import com.justparokq.homeftp.shared.ftp.PagedDirectoryResponse
import com.justparokq.homeftp.shared.ftp.isImage
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

internal fun Route.addFtpRoutes(ftpDependencies: FtpDependencies) {
    get("/image") {
        val path = call.parameters["path"]
        val preview = call.parameters["preview"].toBoolean()

        when {
            path.isNullOrEmpty() -> call.respond(HttpStatusCode.BadRequest, "Empty path param")
            else -> {
                val file = ftpDependencies.communicator.getFile(path)

                if (file == null || !isImage(file.extension)) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        "File not found or file extension is not supported"
                    )
                } else {
                    // todo rework
                    val fileToSend = if (preview) {
                        ftpDependencies.photoProcessor.getPreviewForPhoto(file)
                    } else file
                    call.respondFile(fileToSend)
                }
            }
        }
    }

    get("/directory") {
        val path = call.parameters["path"] ?: ""
        val page = call.parameters["page"]?.toIntOrNull() ?: 0
        val pageSize = call.parameters["pageSize"]?.toIntOrNull() ?: 30

        val allFiles = ftpDependencies.communicator.getDirectoryContent(path) ?: listOf()
        val pagedFiles = allFiles
            .drop(page * pageSize)
            .take(pageSize)

        val hasNextPage = (page + 1) * pageSize < allFiles.size
        val dtos = ftpDependencies.mapper.map(pagedFiles)

        call.respond(
            PagedDirectoryResponse(
                files = dtos,
                hasNextPage = hasNextPage
            )
        )
    }
}
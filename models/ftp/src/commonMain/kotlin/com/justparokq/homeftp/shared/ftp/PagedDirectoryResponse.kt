package com.justparokq.homeftp.shared.ftp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedDirectoryResponse(
    @SerialName("files")
    val files: List<FileResponse>,
    @SerialName("hasNextPage")
    val hasNextPage: Boolean
)
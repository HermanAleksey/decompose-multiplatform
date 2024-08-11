package com.justparokq.homeftp.models.ftp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileResponse(
    @SerialName("uri")
    val uri: String,
    @SerialName("name")
    val name: String,
    @SerialName("isFolder")
    val isDirectory: Boolean,
)

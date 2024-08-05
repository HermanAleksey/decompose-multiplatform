package com.justparokq.homeftp.models.ftp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDirectoryContentRequest(
    @SerialName("uri")
    val uri: String,
)
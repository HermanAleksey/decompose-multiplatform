package com.justparokq.homeftp.shared.ftp.model

data class PaginationState(
    val currentPage: Int = 0,
    val hasNextPage: Boolean = true,
    val isLoadingNextPage: Boolean = false,
    val error: String? = null,
)
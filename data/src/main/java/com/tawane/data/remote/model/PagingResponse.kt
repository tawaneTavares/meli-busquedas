package com.tawane.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagingResponse(
    val total: Int?,
    val offset: Int,
    val limit: Int,
    @Json(name = "primary_results") val primaryResults: Int,
)

package com.tawane.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "site_id") val siteId: String,
    val paging: PagingResponse,
    val results: List<ResultResponse>?,
)

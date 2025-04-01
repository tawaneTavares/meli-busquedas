package com.tawane.data.remote.service

import com.tawane.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MeliService {

    @GET("sites/{site_id}/search/{q}")
    suspend fun searchItems(
        @Path("site_id") siteId: String,
        @Path("q") query: String,
        @Path("page_position") position: Int,
    ): ApiResponse
}

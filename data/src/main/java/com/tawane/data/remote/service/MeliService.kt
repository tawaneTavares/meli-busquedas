package com.tawane.data.remote.service

import com.tawane.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliService {

    @GET("sites/{siteID}/search")
    suspend fun searchItems(
        @Path("siteID") siteID: String,
        @Query("q") text: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): ApiResponse
}

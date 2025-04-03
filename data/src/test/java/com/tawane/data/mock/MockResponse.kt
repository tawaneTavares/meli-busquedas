package com.tawane.data.mock

import com.tawane.data.remote.model.ApiResponse
import com.tawane.data.remote.model.PagingResponse
import com.tawane.data.remote.model.ResultResponse
import com.tawane.domain.model.SearchItem

object MockResponse {

    private val resultResponse = ResultResponse(
        siteId = "MLA",
        title = "Test",
        price = 10.0,
        currencyId = "ARS",
        thumbnail = "http://test.com",
        installments = null,
        shipping = null,
        attributes = emptyList(),
        officialStoreId = null,
        officialStoreName = null,
    )

    val apiResponse = ApiResponse(
        siteId = "MLA",
        paging = PagingResponse(total = 1, offset = 0, limit = 1),
        results = listOf(resultResponse),
    )

    val emptyApiResponse = ApiResponse(
        siteId = "MLA",
        paging = PagingResponse(total = 1, offset = 0, limit = 1),
        results = emptyList(),
    )

    private val searchItem = SearchItem(
        siteId = "MLA",
        title = "Test",
        price = 10.0,
        currencyId = "ARS",
        thumbnail = "http://test.com",
        installments = null,
        shipping = null,
        attributes = emptyList(),
        officialStoreId = null,
        officialStoreName = null,
    )

    val listSearchItem = listOf(searchItem)
}

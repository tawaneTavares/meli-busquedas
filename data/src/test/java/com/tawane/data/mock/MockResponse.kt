package com.tawane.data.mock

import com.tawane.data.local.entity.LastViewedItemEntity
import com.tawane.data.local.utils.getDateLongNow
import com.tawane.data.remote.model.ApiResponse
import com.tawane.data.remote.model.PagingResponse
import com.tawane.data.remote.model.ResultResponse
import com.tawane.domain.model.SearchItem

object MockResponse {

    private val resultResponse = ResultResponse(
        id = "testId",
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

    private val searchItemMock = SearchItem(
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

    val listSearchItem = listOf(searchItemMock)

    const val QUERY = "Test"

    val lastViewedItem = LastViewedItemEntity(
        id = "MLA123",
        lastViewedDate = getDateLongNow(),
        searchItemJson = "{}",
    )

    val expectedList = listOf(lastViewedItem)

    private fun mockSearchItem(id: String) = SearchItem(
        id = id,
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

    val expectedListWithId = listOf(
        mockSearchItem(id = "MLA1"),
        mockSearchItem(id = "MLA2"),
    )
}

package com.tawane.meli.ui.mock

import com.tawane.domain.model.SearchItem

object MockApp {
    fun mockSearchItem(id: String) = SearchItem(
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

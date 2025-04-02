package com.tawane.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchItem(
    val siteId: String? = "",
    val title: String? = "",
    val price: Double? = 0.0,
    val currencyId: String? = "",
    val thumbnail: String? = "",
    val installments: Installments?,
    val shipping: Shipping?,
    val attributes: List<Attribute>,
    val officialStoreId: Int?,
    val officialStoreName: String? = "",
)

@JsonClass(generateAdapter = true)
data class Installments(val quantity: Int, val amount: Double? = 0.0, val rate: Double? = 0.0)

@JsonClass(generateAdapter = true)
data class Shipping(val freeShipping: Boolean? = false)

@JsonClass(generateAdapter = true)
data class Attribute(val name: String? = "", val valueName: String? = "")

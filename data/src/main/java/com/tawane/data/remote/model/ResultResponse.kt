package com.tawane.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultResponse(
    val id: String,
    @Json(name = "site_id") val siteId: String,
    val title: String,
    val price: Double,
    @Json(name = "currency_id") val currencyId: String,
    val thumbnail: String,
    val installments: InstallmentsResponse?,
    val shipping: ShippingResponse?,
    val attributes: List<AttributeResponse>,
    @Json(name = "official_store_id") val officialStoreId: Int?,
    @Json(name = "official_store_name") val officialStoreName: String?,
)

@JsonClass(generateAdapter = true)
data class InstallmentsResponse(val quantity: Int, val amount: Double, val rate: Double)

@JsonClass(generateAdapter = true)
data class ShippingResponse(@Json(name = "free_shipping") val freeShipping: Boolean)

@JsonClass(generateAdapter = true)
data class AttributeResponse(val name: String, @Json(name = "value_name") val valueName: String?)

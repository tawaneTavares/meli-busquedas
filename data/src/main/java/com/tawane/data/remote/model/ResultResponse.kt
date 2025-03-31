package com.tawane.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultResponse(
    val id: String,
    @Json(name = "site_id") val siteId: String,
    val title: String,
    val seller: SellerResponse,
    val price: Double,
    @Json(name = "currency_id") val currencyId: String,
    @Json(name = "available_quantity") val availableQuantity: Int,
    @Json(name = "buying_mode") val buyingMode: String,
    @Json(name = "listing_type_id") val listingTypeId: String,
    @Json(name = "stop_time") val stopTime: String,
    val condition: String,
    val permalink: String,
    val thumbnail: String,
    @Json(name = "accepts_mercadopago") val acceptsMercadoPago: Boolean,
    val installments: InstallmentsResponse?,
    val shipping: ShippingResponse,
    val attributes: List<AttributeResponse>,
    @Json(name = "original_price") val originalPrice: Double?,
    @Json(name = "category_id") val categoryId: String,
    @Json(name = "official_store_id") val officialStoreId: Int?,
    @Json(name = "catalog_product_id") val catalogProductId: String?,
    val tags: List<String>?,
    @Json(name = "catalog_listing") val catalogListing: Boolean?,
    val address: AddressResponse?,
    @Json(name = "seller_address") val sellerAddress: SellerAddressResponse?,
    @Json(name = "differential_pricing") val differentialPricing: DifferentialPricingResponse?,
)

@JsonClass(generateAdapter = true)
data class SellerResponse(
    val id: Long,
    val nickname: String? = null,
    @Json(name = "power_seller_status") val powerSellerStatus: String?,
    @Json(name = "car_dealer") val carDealer: Boolean?,
    @Json(name = "real_estate_agency") val realEstateAgency: Boolean?,
    val tags: List<String>?,
)

@JsonClass(generateAdapter = true)
data class InstallmentsResponse(
    val quantity: Int,
    val amount: Double,
    val rate: Double,
    @Json(name = "currency_id") val currencyId: String,
)

@JsonClass(generateAdapter = true)
data class ShippingResponse(
    @Json(name = "free_shipping") val freeShipping: Boolean,
    val mode: String,
    val tags: List<String>,
    @Json(name = "logistic_type") val logisticType: String,
    @Json(name = "store_pick_up") val storePickUp: Boolean,
)

@JsonClass(generateAdapter = true)
data class AttributeResponse(
    val id: String,
    val name: String,
    @Json(name = "value_id") val valueId: String?,
    @Json(name = "value_name") val valueName: String?,
    @Json(name = "value_struct") val valueStruct: Any?,
    val values: List<AttributeValueResponse>,
    val source: Long,
    @Json(name = "attribute_group_id") val attributeGroupId: String,
    @Json(name = "attribute_group_name") val attributeGroupName: String,
)

@JsonClass(generateAdapter = true)
data class AttributeValueResponse(val id: String?, val name: String?, val struct: Any?, val source: Long)

@JsonClass(generateAdapter = true)
data class AddressResponse(
    @Json(name = "state_id") val stateId: String,
    @Json(name = "state_name") val stateName: String,
    @Json(name = "city_id") val cityId: String,
    @Json(name = "city_name") val cityName: String,
)

@JsonClass(generateAdapter = true)
data class SellerAddressResponse(
    val country: GenericResponse,
    val state: GenericResponse,
    val city: GenericResponse,
    val latitude: String?,
    val longitude: String?,
)

@JsonClass(generateAdapter = true)
data class GenericResponse(val id: String, val name: String)

@JsonClass(generateAdapter = true)
data class DifferentialPricingResponse(val id: Long)

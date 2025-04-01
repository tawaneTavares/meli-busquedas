package com.tawane.domain.model

data class SearchItem(
    val id: String,
    val siteId: String,
    val title: String,
    val seller: Seller,
    val price: Double,
    val currencyId: String,
    val availableQuantity: Int,
    val buyingMode: String,
    val listingTypeId: String,
    val stopTime: String,
    val condition: String,
    val permalink: String,
    val thumbnail: String,
    val acceptsMercadoPago: Boolean,
    val installments: Installments?,
    val shipping: Shipping,
    val attributes: List<Attribute>,
    val originalPrice: Double?,
    val categoryId: String,
    val officialStoreId: Int?,
    val catalogProductId: String?,
    val tags: List<String>?,
    val catalogListing: Boolean?,
    val address: Address?,
    val sellerAddress: SellerAddress?,
    val differentialPricing: DifferentialPricing?,
)

data class Seller(
    val id: Long,
    val nickname: String? = null,
    val powerSellerStatus: String?,
    val carDealer: Boolean?,
    val realEstateAgency: Boolean?,
    val tags: List<String>?,
)

data class Installments(val quantity: Int, val amount: Double, val rate: Double, val currencyId: String)

data class Shipping(
    val freeShipping: Boolean,
    val mode: String,
    val tags: List<String>,
    val logisticType: String,
    val storePickUp: Boolean,
)

data class Attribute(
    val id: String,
    val name: String,
    val valueId: String?,
    val valueName: String?,
    val valueStruct: Any?,
    val values: List<AttributeValue>,
    val source: Long,
    val attributeGroupId: String,
    val attributeGroupName: String,
)

data class AttributeValue(val id: String?, val name: String?, val struct: Any?, val source: Long)

data class Address(val stateId: String, val stateName: String, val cityId: String, val cityName: String)

data class SellerAddress(
    val country: GenericResponse,
    val state: GenericResponse,
    val city: GenericResponse,
    val latitude: String?,
    val longitude: String?,
)

data class GenericResponse(val id: String, val name: String)

data class DifferentialPricing(val id: Long)

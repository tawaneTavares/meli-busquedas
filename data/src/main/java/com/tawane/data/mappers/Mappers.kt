package com.tawane.data.mappers

import com.tawane.data.remote.model.AddressResponse
import com.tawane.data.remote.model.AttributeResponse
import com.tawane.data.remote.model.AttributeValueResponse
import com.tawane.data.remote.model.DifferentialPricingResponse
import com.tawane.data.remote.model.GenericResponse
import com.tawane.data.remote.model.InstallmentsResponse
import com.tawane.data.remote.model.ResultResponse
import com.tawane.data.remote.model.SellerAddressResponse
import com.tawane.data.remote.model.SellerResponse
import com.tawane.data.remote.model.ShippingResponse
import com.tawane.domain.model.Address as DomainAddress
import com.tawane.domain.model.Attribute as DomainAttribute
import com.tawane.domain.model.AttributeValue as DomainAttributeValue
import com.tawane.domain.model.DifferentialPricing as DomainDifferentialPricing
import com.tawane.domain.model.GenericResponse as DomainGenericResponse
import com.tawane.domain.model.Installments as DomainInstallments
import com.tawane.domain.model.SearchItem
import com.tawane.domain.model.Seller as DomainSeller
import com.tawane.domain.model.SellerAddress as DomainSellerAddress
import com.tawane.domain.model.Shipping as DomainShipping

object Mappers {
    fun ResultResponse.toDomainModel(): SearchItem = SearchItem(
        id = id,
        siteId = siteId,
        title = title,
        seller = seller.toDomainModel(),
        price = price,
        currencyId = currencyId,
        availableQuantity = availableQuantity,
        buyingMode = buyingMode,
        listingTypeId = listingTypeId,
        stopTime = stopTime,
        condition = condition,
        permalink = permalink,
        thumbnail = thumbnail,
        acceptsMercadoPago = acceptsMercadoPago,
        installments = installments?.toDomainModel(),
        shipping = shipping.toDomainModel(),
        attributes = attributes.map { it.toDomainModel() },
        originalPrice = originalPrice,
        categoryId = categoryId,
        officialStoreId = officialStoreId,
        catalogProductId = catalogProductId,
        tags = tags,
        catalogListing = catalogListing,
        address = address?.toDomainModel(),
        sellerAddress = sellerAddress?.toDomainModel(),
        differentialPricing = differentialPricing?.toDomainModel(),
    )

    fun SellerResponse.toDomainModel(): DomainSeller = DomainSeller(
        id = id,
        nickname = nickname,
        powerSellerStatus = powerSellerStatus,
        carDealer = carDealer,
        realEstateAgency = realEstateAgency,
        tags = tags,
    )

    fun InstallmentsResponse.toDomainModel(): DomainInstallments = DomainInstallments(
        quantity = quantity,
        amount = amount,
        rate = rate,
        currencyId = currencyId,
    )

    fun ShippingResponse.toDomainModel(): DomainShipping = DomainShipping(
        freeShipping = freeShipping,
        mode = mode,
        tags = tags,
        logisticType = logisticType,
        storePickUp = storePickUp,
    )

    fun AttributeResponse.toDomainModel(): DomainAttribute = DomainAttribute(
        id = id,
        name = name,
        valueId = valueId,
        valueName = valueName,
        valueStruct = valueStruct,
        values = values.map { it.toDomainModel() },
        source = source,
        attributeGroupId = attributeGroupId,
        attributeGroupName = attributeGroupName,
    )

    fun AttributeValueResponse.toDomainModel(): DomainAttributeValue = DomainAttributeValue(
        id = id,
        name = name,
        struct = struct,
        source = source,
    )

    fun AddressResponse.toDomainModel(): DomainAddress = DomainAddress(
        stateId = stateId,
        stateName = stateName,
        cityId = cityId,
        cityName = cityName,
    )

    fun SellerAddressResponse.toDomainModel(): DomainSellerAddress = DomainSellerAddress(
        country = country.toDomainModel(),
        state = state.toDomainModel(),
        city = city.toDomainModel(),
        latitude = latitude,
        longitude = longitude,
    )

    fun GenericResponse.toDomainModel(): DomainGenericResponse = DomainGenericResponse(
        id = id,
        name = name,
    )

    fun DifferentialPricingResponse.toDomainModel(): DomainDifferentialPricing = DomainDifferentialPricing(id = id)
}

package com.tawane.data.mappers

import com.tawane.data.remote.model.AttributeResponse
import com.tawane.data.remote.model.InstallmentsResponse
import com.tawane.data.remote.model.ResultResponse
import com.tawane.data.remote.model.ShippingResponse
import com.tawane.domain.model.Attribute as DomainAttribute
import com.tawane.domain.model.Installments as DomainInstallments
import com.tawane.domain.model.SearchItem
import com.tawane.domain.model.Shipping as DomainShipping

fun ResultResponse.toDomainModel(): SearchItem = SearchItem(
    id = id,
    siteId = siteId,
    title = title,
    price = price,
    currencyId = currencyId,
    thumbnail = thumbnail,
    installments = installments?.toDomainModel(),
    shipping = shipping?.toDomainModel(),
    attributes = attributes.map { it.toDomainModel() },
    officialStoreId = officialStoreId,
    officialStoreName = officialStoreName,
)

fun InstallmentsResponse.toDomainModel(): DomainInstallments = DomainInstallments(
    quantity = quantity,
    amount = amount,
    rate = rate,
)

fun ShippingResponse.toDomainModel(): DomainShipping = DomainShipping(
    freeShipping = freeShipping,
)

fun AttributeResponse.toDomainModel(): DomainAttribute = DomainAttribute(
    name = name,
    valueName = valueName,
)

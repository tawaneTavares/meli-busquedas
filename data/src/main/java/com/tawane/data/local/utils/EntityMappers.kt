package com.tawane.data.local.utils

import com.squareup.moshi.Moshi
import com.tawane.data.local.entity.LastViewedItemEntity
import com.tawane.domain.model.SearchItem

fun SearchItem.toJson(moshi: Moshi): String {
    val jsonAdapter = moshi.adapter(SearchItem::class.java)
    return jsonAdapter.toJson(this)
}

fun String.fromJsonToSearchItem(moshi: Moshi): SearchItem? {
    val jsonAdapter = moshi.adapter(SearchItem::class.java)
    return jsonAdapter.fromJson(this)
}

fun SearchItem.toLastViewedItemEntity(moshi: Moshi): LastViewedItemEntity = LastViewedItemEntity(
    id = id ?: "",
    lastViewedDate = getDateLongNow(),
    searchItemJson = toJson(moshi),
)

package com.tawane.data.local

import com.tawane.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface ILastViewedLocalData {

    suspend fun saveLastViewedItem(searchItem: SearchItem)
    fun getLastViewedItems(): Flow<List<SearchItem>>
}

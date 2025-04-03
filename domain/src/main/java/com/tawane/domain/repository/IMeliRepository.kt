package com.tawane.domain.repository

import androidx.paging.PagingData
import com.tawane.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface IMeliRepository {
    fun searchItems(query: String): Flow<PagingData<SearchItem>>
    fun getLastViewedItems(): Flow<List<SearchItem>>
    suspend fun saveLastViewedItem(searchItem: SearchItem)
}

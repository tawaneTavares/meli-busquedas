package com.tawane.data.remote.datasource

import androidx.paging.PagingData
import com.tawane.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    fun searchItems(query: String): Flow<PagingData<SearchItem>>
}

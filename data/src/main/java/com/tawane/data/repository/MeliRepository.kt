package com.tawane.data.repository

import androidx.paging.PagingData
import com.tawane.data.local.ILastViewedLocalData
import com.tawane.data.remote.datasource.IRemoteDataSource
import com.tawane.domain.model.SearchItem
import com.tawane.domain.repository.IMeliRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class MeliRepository @Inject constructor(
    private val dataSource: IRemoteDataSource,
    private val localData: ILastViewedLocalData,
) : IMeliRepository {

    override fun searchItems(query: String): Flow<PagingData<SearchItem>> = dataSource.searchItems(query).flowOn(IO)

    override fun getLastViewedItems(): Flow<List<SearchItem>> = localData.getLastViewedItems()
        .flowOn(IO)

    override suspend fun saveLastViewedItem(searchItem: SearchItem) {
        localData.saveLastViewedItem(searchItem)
    }
}

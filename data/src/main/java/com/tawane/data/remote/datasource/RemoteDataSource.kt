package com.tawane.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tawane.data.mappers.toDomainModel
import com.tawane.data.remote.paging.SearchPaging
import com.tawane.data.remote.service.MeliService
import com.tawane.domain.model.SearchItem
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val PAGE_SIZE = 10
private const val INITIAL_LOAD = 15

class RemoteDataSource @Inject constructor(private val myService: MeliService) : IRemoteDataSource {
    override fun searchItems(query: String): Flow<PagingData<SearchItem>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD,
        ),
        pagingSourceFactory = {
            SearchPaging(myService, query)
        },
    ).flow
        .flowOn(IO)
        .map { paging ->
            paging.map { items ->
                items.toDomainModel()
            }
        }
        .catch {
            Timber.e(it)
        }
}

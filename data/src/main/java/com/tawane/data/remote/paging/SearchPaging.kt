package com.tawane.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tawane.data.remote.model.ResultResponse
import com.tawane.data.remote.service.MeliService
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 0
private const val PAGE_SIZE = 50

class SearchPaging(private val service: MeliService, private val query: String) : PagingSource<Int, ResultResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.searchItems("MLB", query, position, PAGE_SIZE)
            val items = response.results
            val totalPages = response.paging.total ?: (STARTING_PAGE_INDEX / PAGE_SIZE)
            val nextKey = if (position >= totalPages) null else position.plus(PAGE_SIZE)

            Timber.d("$nextKey")
            LoadResult.Page(
                data = items ?: emptyList(),
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (exception: Exception) {
            Timber.e("${exception.message}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_SIZE)
        }
}

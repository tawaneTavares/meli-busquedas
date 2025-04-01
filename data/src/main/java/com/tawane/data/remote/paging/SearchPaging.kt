package com.tawane.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tawane.data.remote.model.ResultResponse
import com.tawane.data.remote.service.MeliService
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1

class SearchPaging(private val service: MeliService, private val query: String) : PagingSource<Int, ResultResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.searchItems("MLA", query, position)
            val items = response.results
            val totalPages = response.paging.total ?: STARTING_PAGE_INDEX // TODO: fix paging later
            val nextKey = if (position >= totalPages) null else position.plus(1)

            Timber.d("$nextKey")
            LoadResult.Page(
                data = items ?: emptyList(),
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}

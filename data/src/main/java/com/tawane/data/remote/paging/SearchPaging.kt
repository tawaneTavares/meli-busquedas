package com.tawane.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tawane.data.remote.model.ApiResponse
import com.tawane.data.remote.model.ResultResponse
import com.tawane.data.remote.service.MeliService
import javax.inject.Inject
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 0
private const val PAGE_SIZE = 50

class SearchPaging(private val service: MeliService, private val query: String) :
    PagingSource<Int, ResultResponse>() {

    @Inject
    lateinit var moshi: Moshi

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            // TODO atention: this is a workaround to avoid the api call
            val jsonAdapter: JsonAdapter<ApiResponse> = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build().adapter(ApiResponse::class.java)
            val response = jsonAdapter.fromJson(query)

            // TODO atention: this is the correct way to call the api
//            val response = service.searchItems("MLB", query, position, PAGE_SIZE)
            val items = response?.results
            val totalPages = response?.paging?.total ?: (STARTING_PAGE_INDEX / PAGE_SIZE)
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

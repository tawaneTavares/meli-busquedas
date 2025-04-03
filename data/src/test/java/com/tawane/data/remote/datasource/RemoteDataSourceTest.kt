package com.tawane.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.cash.turbine.test
import com.tawane.data.mock.MockResponse.QUERY
import com.tawane.data.mock.MockResponse.apiResponse
import com.tawane.data.mock.MockResponse.emptyApiResponse
import com.tawane.data.remote.model.ResultResponse
import com.tawane.data.remote.paging.SearchPaging
import com.tawane.data.remote.service.MeliService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class RemoteDataSourceTest {

    private lateinit var meliService: MeliService
    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var pagingSource: SearchPaging

    @Before
    fun setup() {
        meliService = mockk()
        remoteDataSource = RemoteDataSource(meliService)
        pagingSource = SearchPaging(meliService, QUERY)
    }

    // TODO atention: this tests are failing because we are using a json string instead of a real api response

    @Test
    fun `Paging Source Load Success`() = runTest {
        coEvery { meliService.searchItems(eq("MLB"), eq(QUERY), eq(0), eq(50)) } returns apiResponse

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 50,
                placeholdersEnabled = false,
            ),
        )

        assert(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page<Int, ResultResponse>
        assertEquals(null, result.prevKey)
        assertEquals(50, result.nextKey)
        assertEquals(apiResponse.results, result.data)
    }

    @Test
    fun `Paging Source update next key Success`() = runTest {
        val currentPage = 0
        val pageSize = 50
        coEvery { meliService.searchItems(eq("MLB"), eq(QUERY), eq(currentPage), eq(pageSize)) } returns apiResponse

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = currentPage,
                loadSize = pageSize,
                placeholdersEnabled = false,
            ),
        )

        assert(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page<Int, ResultResponse>
        assertEquals(null, result.prevKey)
        assertEquals(currentPage + pageSize, result.nextKey)
        assertEquals(apiResponse.results, result.data)

        assertEquals(null, result.prevKey)
        assertEquals(50, result.nextKey)
        assertEquals(apiResponse.results, result.data)
    }

    @Test
    fun `Paging Source Load Error`() = runTest {
        val exception = RuntimeException("Simulated API error")
        coEvery { meliService.searchItems(eq("MLB"), eq(QUERY), eq(0), eq(50)) } throws exception

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 50,
                placeholdersEnabled = false,
            ),
        )

        assert(result is PagingSource.LoadResult.Error)
        result as PagingSource.LoadResult.Error<Int, ResultResponse>
        assertEquals(exception, result.throwable)
    }

    @Test
    fun `Paging Source empty list`() = runTest {
        coEvery { meliService.searchItems(eq("MLB"), eq(QUERY), eq(0), eq(50)) } returns emptyApiResponse

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 50,
                placeholdersEnabled = false,
            ),
        )

        assert(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page<Int, ResultResponse>
        assert(result.data.isEmpty())
        assertEquals(null, result.prevKey)
        assertEquals(50, result.nextKey)
    }

    @Test
    fun `searchItems emit success`() = runTest {
        coEvery { meliService.searchItems(eq("MLB"), eq(QUERY), eq(0), eq(50)) } returns apiResponse

        remoteDataSource.searchItems(QUERY).test {
            val pagingData = awaitItem()
            assertNotNull(pagingData)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchItems emit success and awaitComplete`() = runTest {
        coEvery { meliService.searchItems(eq("MLB"), eq(QUERY), eq(0), eq(50)) } returns apiResponse

        remoteDataSource.searchItems(QUERY)
            .take(1)
            .test(timeout = 5.seconds) {
                val pagingData = awaitItem()
                assertNotNull(pagingData)
                awaitComplete()
            }
    }

    @Test
    fun `searchItems emit error`() = runTest {
        val exception = RuntimeException("Simulated API error")
        coEvery { meliService.searchItems(eq("MLB"), eq(QUERY), eq(0), eq(50)) } throws exception

        remoteDataSource.searchItems(QUERY)
            .catch { error ->
                assertEquals(exception.message, error.message)
            }
            .test {
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `Paging Source refresh key`() = runTest {
        val state = mockk<PagingState<Int, ResultResponse>>()
        coEvery { state.anchorPosition } returns 0
        coEvery { state.closestPageToPosition(0) } returns mockk {
            every { prevKey } returns 0
            every { nextKey } returns 100
        }

        val result = pagingSource.getRefreshKey(state)
        assertEquals(50, result)
    }
}

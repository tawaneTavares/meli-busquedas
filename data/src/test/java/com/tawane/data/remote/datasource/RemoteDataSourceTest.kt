package com.tawane.data.remote.datasource

import androidx.paging.PagingSource
import app.cash.turbine.test
import com.tawane.data.mock.MockResponse.apiResponse
import com.tawane.data.mock.MockResponse.emptyApiResponse
import com.tawane.data.remote.model.ResultResponse
import com.tawane.data.remote.paging.SearchPaging
import com.tawane.data.remote.service.MeliService
import io.mockk.coEvery
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
        pagingSource = SearchPaging(meliService, "Test")
    }

    // TODO atention: this tests are failing because we are using a json string instead of a real api response

    @Test
    fun `Paging Source Load Success`() = runTest {
        coEvery { meliService.searchItems(eq("MLB"), eq("Test"), eq(0), eq(50)) } returns apiResponse

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
        coEvery { meliService.searchItems(eq("MLB"), eq("Test"), eq(currentPage), eq(pageSize)) } returns apiResponse

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
        coEvery { meliService.searchItems(eq("MLB"), eq("Test"), eq(0), eq(50)) } throws exception

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
        coEvery { meliService.searchItems(eq("MLB"), eq("Test"), eq(0), eq(50)) } returns emptyApiResponse

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
        coEvery { meliService.searchItems(eq("MLB"), eq("Test"), eq(0), eq(50)) } returns apiResponse

        remoteDataSource.searchItems("Test").test {
            val pagingData = awaitItem()
            assertNotNull(pagingData)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchItems emit success and awaitComplete`() = runTest {
        coEvery { meliService.searchItems(eq("MLB"), eq("Test"), eq(0), eq(50)) } returns apiResponse

        remoteDataSource.searchItems("Test")
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
        coEvery { meliService.searchItems(eq("MLB"), eq("Test"), eq(0), eq(50)) } throws exception

        remoteDataSource.searchItems("Test")
            .catch { error ->
                assertEquals(exception.message, error.message)
            }
            .test {
                cancelAndIgnoreRemainingEvents()
            }
    }
}

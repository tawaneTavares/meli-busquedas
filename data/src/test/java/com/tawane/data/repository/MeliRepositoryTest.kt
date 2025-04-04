package com.tawane.data.repository

import androidx.paging.PagingData
import app.cash.turbine.test
import com.tawane.data.local.ILastViewedLocalData
import com.tawane.data.mock.MockResponse.QUERY
import com.tawane.data.mock.MockResponse.expectedListWithId
import com.tawane.data.mock.MockResponse.listSearchItem
import com.tawane.data.remote.datasource.IRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MeliRepositoryTest {

    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var repository: MeliRepository
    private lateinit var localData: ILastViewedLocalData

    @Before
    fun setup() {
        remoteDataSource = mockk()
        localData = mockk()
        repository = MeliRepository(remoteDataSource, localData)
    }

    @Test
    fun `search item return paging data Success`() = runTest {
        val pagingData = PagingData.from(listSearchItem)
        every { remoteDataSource.searchItems(QUERY) } returns flowOf(pagingData)

        val result = repository.searchItems(QUERY).first()

        assertNotNull(result)
        coVerify { remoteDataSource.searchItems(QUERY) }
    }

    @Test
    fun `search item return paging data Error`() = runTest {
        val exception = RuntimeException("error")
        every { remoteDataSource.searchItems(QUERY) } throws exception

        try {
            repository.searchItems(QUERY).first()
            assert(false) { "exception" }
        } catch (e: Exception) {
            assertEquals(exception.message, e.message)
        }
    }

    @Test
    fun `search item emit paging data Success`() = runTest {
        val pagingData = PagingData.from(listSearchItem)
        coEvery { remoteDataSource.searchItems(QUERY) } returns flowOf(pagingData)

        repository.searchItems(QUERY)
            .take(1)
            .test(timeout = 5.seconds) {
                val result = awaitItem()
                assertNotNull(result)
                awaitComplete()
            }

        coVerify { remoteDataSource.searchItems(QUERY) }
    }

    @Test
    fun `get last viewed items should return correct list`() = runTest {
        coEvery { localData.getLastViewedItems() } returns flowOf(expectedListWithId)

        repository.getLastViewedItems().test {
            val actualList = awaitItem()
            assert(actualList == expectedListWithId)
            awaitComplete()
        }
    }

    @Test
    fun `get last viewed items should emit correct list`() = runTest {
        coEvery { localData.getLastViewedItems() } returns flowOf(expectedListWithId)

        repository.getLastViewedItems()
            .take(1)
            .test(timeout = 5.seconds) {
                val actualList = awaitItem()
                assert(actualList == expectedListWithId)
                awaitComplete()
            }
    }

    @Test
    fun `get last viewed items should return error`() = runTest {
        val exception = RuntimeException("error")
        coEvery { localData.getLastViewedItems() } throws exception

        try {
            repository.getLastViewedItems().first()
            assert(false) { "exception" }
        } catch (e: Exception) {
            assertEquals(exception.message, e.message)
        }
    }
}

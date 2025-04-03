package com.tawane.data.repository

import androidx.paging.PagingData
import app.cash.turbine.test
import com.tawane.data.mock.MockResponse.listSearchItem
import com.tawane.data.remote.datasource.IRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MeliRepositoryTest {

    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var repository: MeliRepository

    @Before
    fun setup() {
        remoteDataSource = mockk()
        repository = MeliRepository(remoteDataSource)
    }

    @Test
    fun `search item return paging data Success`() = runTest {
        val pagingData = PagingData.from(listSearchItem)
        every { remoteDataSource.searchItems("Test") } returns flowOf(pagingData)

        val result = repository.searchItems("Test").first()

        assertNotNull(result)
        coVerify { remoteDataSource.searchItems("Test") }
    }

    @Test
    fun `search item return paging data Error`() = runTest {
        val exception = RuntimeException("error")
        every { remoteDataSource.searchItems("Test") } throws exception

        try {
            repository.searchItems("Test").first()
            assert(false) { "exception" }
        } catch (e: Exception) {
            assertEquals(exception.message, e.message)
        }
    }

    @Test
    fun `search item emit paging data Success`() = runTest {
        val pagingData = PagingData.from(listSearchItem)
        coEvery { remoteDataSource.searchItems("Test") } returns flowOf(pagingData)

        repository.searchItems("Test")
            .take(1)
            .test(timeout = 5.seconds) {
                val result = awaitItem()
                assertNotNull(result)
                awaitComplete()
            }

        coVerify { remoteDataSource.searchItems("Test") }
    }
}

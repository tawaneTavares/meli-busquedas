package com.tawane.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.tawane.domain.model.SearchItem
import com.tawane.domain.repository.IMeliRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchItemsUseCaseTest {
    private lateinit var repository: IMeliRepository
    private lateinit var useCase: SearchItemsUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SearchItemsUseCase(repository)
    }

    @Test
    fun `invoke should call repository searchItems with correct query`() = runTest {
        val query = "Test"
        val pagingData = PagingData.from(listOf<SearchItem>())
        coEvery { repository.searchItems(query) } returns flowOf(pagingData)

        useCase(query).test {
            awaitItem()
            awaitComplete()
        }

        coVerify(exactly = 1) { repository.searchItems(query) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke should throw exception from repository`() = runTest {
        val query = "test"
        val exception = RuntimeException("Simulated repository error")

        coEvery { repository.searchItems(query) } throws exception

        useCase(query)
    }
}

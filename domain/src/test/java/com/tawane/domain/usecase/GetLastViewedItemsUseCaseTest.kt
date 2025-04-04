package com.tawane.domain.usecase

import app.cash.turbine.test
import com.tawane.domain.mock.MockDomain.expectedListWithId
import com.tawane.domain.repository.IMeliRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetLastViewedItemsUseCaseTest {

    private lateinit var repository: IMeliRepository
    private lateinit var useCase: GetLastViewedItemsUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetLastViewedItemsUseCase(repository)
    }

    @Test
    fun `invoke should call repository getLastViewedItems`() = runTest {
        coEvery { repository.getLastViewedItems() } returns flowOf(expectedListWithId)

        useCase().test {
            val actualList = awaitItem()
            assert(actualList == expectedListWithId)
            awaitComplete()
        }

        coVerify(exactly = 1) { repository.getLastViewedItems() }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke should throw exception from repository`() = runTest {
        val exception = RuntimeException("Simulated repository error")
        coEvery { repository.getLastViewedItems() } throws exception

        useCase()
    }

    @Test
    fun `invoke should emit correct list`() = runTest {
        coEvery { repository.getLastViewedItems() } returns flowOf(expectedListWithId)

        useCase().test(timeout = 5.seconds) {
            val actualList = awaitItem()
            assert(actualList == expectedListWithId)
            awaitComplete()
        }
    }
}

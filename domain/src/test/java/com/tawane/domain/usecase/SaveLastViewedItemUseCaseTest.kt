package com.tawane.domain.usecase

import com.tawane.domain.mock.MockDomain.mockSearchItem
import com.tawane.domain.model.SearchItem
import com.tawane.domain.repository.IMeliRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveLastViewedItemUseCaseTest {

    private lateinit var repository: IMeliRepository
    private lateinit var useCase: SaveLastViewedItemUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk(relaxed = true)
        useCase = SaveLastViewedItemUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should call repository saveLastViewedItem with correct item`() = runTest {
        val searchItem: SearchItem = mockSearchItem(id = "MLA1")

        useCase(searchItem)

        coVerify { repository.saveLastViewedItem(searchItem) }
    }
}

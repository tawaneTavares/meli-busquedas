package com.tawane.meli.ui.product

import app.cash.turbine.test
import com.tawane.domain.model.SearchItem
import com.tawane.domain.usecase.GetLastViewedItemsUseCase
import com.tawane.domain.usecase.SaveLastViewedItemUseCase
import com.tawane.meli.CoroutinesTestRule
import com.tawane.meli.ui.mock.MockApp.expectedListWithId
import com.tawane.meli.ui.mock.MockApp.mockSearchItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private lateinit var saveLastViewedItemUseCase: SaveLastViewedItemUseCase
    private lateinit var getLastViewedItemsUseCase: GetLastViewedItemsUseCase
    private lateinit var viewModel: ProductDetailViewModel

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setup() {
        saveLastViewedItemUseCase = mockk()
        getLastViewedItemsUseCase = mockk()
        viewModel = ProductDetailViewModel(saveLastViewedItemUseCase, getLastViewedItemsUseCase)
    }

    @Test
    fun `applyProductDetail should update productDetails`() = runTest {
        val searchItem = SearchItem(
            siteId = "MLA",
            title = "Test",
            price = 10.0,
            currencyId = "ARS",
            thumbnail = "http://test.com",
            installments = null,
            shipping = null,
            attributes = emptyList(),
            officialStoreId = null,
            officialStoreName = null,
        )
        viewModel.applyProductDetail(searchItem)

        viewModel.uiState.test {
            val state = awaitItem()
            assertNotNull(state.productDetails)
        }
    }

    @Test
    fun `saveLastViewedItem should call saveLastViewedItemUseCase`() = runTest {
        val item = mockSearchItem(id = "MLA1")

        coEvery { saveLastViewedItemUseCase(any()) } returns Unit

        viewModel.saveLastViewedItem(item)

        coVerify { saveLastViewedItemUseCase(item) }
    }

    @Test
    fun `getLastViewedItems should update uiState with last viewed items`() = runTest {
        coEvery { getLastViewedItemsUseCase() } returns flowOf(expectedListWithId)

        viewModel.getLastViewedItems()

        viewModel.uiState.test(timeout = 5.seconds) {
            val state = awaitItem()
            Assert.assertEquals(expectedListWithId, state.lastViewedItems)
        }
        coVerify { getLastViewedItemsUseCase() }
    }
}

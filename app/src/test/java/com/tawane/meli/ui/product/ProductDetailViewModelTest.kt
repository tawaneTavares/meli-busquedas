package com.tawane.meli.ui.product

import app.cash.turbine.test
import com.tawane.domain.model.SearchItem
import com.tawane.meli.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private lateinit var viewModel: ProductDetailViewModel

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = ProductDetailViewModel()
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
}

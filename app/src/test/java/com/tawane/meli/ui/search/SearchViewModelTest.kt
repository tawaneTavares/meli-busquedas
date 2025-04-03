package com.tawane.meli.ui.search

import androidx.paging.PagingData
import app.cash.turbine.test
import com.tawane.domain.model.SearchItem
import com.tawane.domain.usecase.SearchItemsUseCase
import com.tawane.meli.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var searchItemsUseCase: SearchItemsUseCase
    private lateinit var viewModel: SearchViewModel

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setup() {
        searchItemsUseCase = mockk()
        viewModel = SearchViewModel(searchItemsUseCase)
    }

    @Test
    fun `search item return empty state when query is empty`() = runTest {
        viewModel.onQueryChange("")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.query)
            assertNull(state.searchResults)
            assertFalse(state.showMinimumCharactersMessage)
        }
    }

    @Test
    fun `search item show minimum characters message when query has less than three characters`() = runTest {
        val pagingData = PagingData.empty<SearchItem>()
        val twoCharactersQuery = "Te"
        coEvery { searchItemsUseCase(twoCharactersQuery) } returns flowOf(pagingData)

        viewModel.onQueryChange(twoCharactersQuery)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(twoCharactersQuery, state.query)
            assertTrue(state.showMinimumCharactersMessage)
        }
    }

    @Test
    fun `search item success when query has three or more characters`() = runTest {
        val pagingData = PagingData.from(listOf<SearchItem>())
        val threeCharactersQuery = "Tes"
        coEvery { searchItemsUseCase(threeCharactersQuery) } returns flowOf(pagingData)

        viewModel.onQueryChange(threeCharactersQuery)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(threeCharactersQuery, state.query)
            assertFalse(state.showMinimumCharactersMessage)
        }
    }
}

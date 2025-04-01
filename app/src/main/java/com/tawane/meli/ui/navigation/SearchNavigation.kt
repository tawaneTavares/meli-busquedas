package com.tawane.meli.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tawane.domain.model.SearchItem
import com.tawane.meli.ui.navigation.ScreensDestinations.SearchScreenDestination
import com.tawane.meli.ui.search.SearchScreen
import com.tawane.meli.ui.search.SearchViewModel

fun NavGraphBuilder.searchScreen(onNavigateToProductDetails: (SearchItem) -> Unit) {
    composable(SearchScreenDestination.route) {
        val viewModel = hiltViewModel<SearchViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        SearchScreen(
            uiState = uiState,
            onQueryChange = viewModel::onQueryChange,
            onItemClick = onNavigateToProductDetails,
        )
    }
}

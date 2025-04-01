package com.tawane.meli.ui.search

import androidx.paging.PagingData
import com.tawane.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

data class SearchUiState(
    val query: String = "",
    val searchResults: Flow<PagingData<SearchItem>>? = null,
    val showMinimumCharactersMessage: Boolean = false,
)

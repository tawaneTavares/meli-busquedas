package com.tawane.meli.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tawane.domain.usecase.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val MINIMUM_QUERY_LENGTH = 3

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchItemsUseCase: SearchItemsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun onQueryChange(newQuery: String, fileMock: String) {
        _uiState.update { state ->
            state.copy(
                query = newQuery,
                searchResults = if (newQuery.isNotEmpty() && newQuery.length >= MINIMUM_QUERY_LENGTH) {
                    // TODO atention: this is a workaround to avoid the api call, it can be replaced by "newQuery" when the api is implemented
                    searchItemsUseCase(fileMock).cachedIn(viewModelScope)
                } else {
                    null
                },
                showMinimumCharactersMessage = newQuery.isNotEmpty() && newQuery.length < MINIMUM_QUERY_LENGTH,
            )
        }
    }
}

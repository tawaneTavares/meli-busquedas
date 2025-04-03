package com.tawane.meli.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawane.domain.model.SearchItem
import com.tawane.domain.usecase.GetLastViewedItemsUseCase
import com.tawane.domain.usecase.SaveLastViewedItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val saveLastViewedItemUseCase: SaveLastViewedItemUseCase,
    private val getLastViewedItemsUseCase: GetLastViewedItemsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun applyProductDetail(product: SearchItem) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                productDetails = product,
            )
        }
    }

    fun saveLastViewedItem(item: SearchItem) {
        saveLastViewedItemUseCase(item)
    }

    fun getLastViewedItems() {
        viewModelScope.launch {
            getLastViewedItemsUseCase.invoke()
                .collect {
                    _uiState.value = _uiState.value.copy(
                        lastViewedItems = it,
                    )
                }
        }
    }
}

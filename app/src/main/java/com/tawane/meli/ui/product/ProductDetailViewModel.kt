package com.tawane.meli.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawane.domain.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun applyProductDetail(product: SearchItem) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                productDetails = product,
            )
        }
    }
}

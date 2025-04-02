package com.tawane.domain.usecase

import androidx.paging.PagingData
import com.tawane.domain.model.SearchItem
import com.tawane.domain.repository.IMeliRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchItemsUseCase @Inject constructor(private val repository: IMeliRepository) {
    operator fun invoke(query: String): Flow<PagingData<SearchItem>> = repository.searchItems(query)
}

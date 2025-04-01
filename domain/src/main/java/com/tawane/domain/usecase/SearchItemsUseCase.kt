package com.tawane.domain.usecase

import androidx.paging.PagingData
import com.tawane.domain.model.SearchItem
import com.tawane.domain.repository.IMeliRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private const val MINIMUM_QUERY_LENGTH = 3

class SearchItemsUseCase @Inject constructor(private val repository: IMeliRepository) {
    operator fun invoke(query: String): Flow<PagingData<SearchItem>> = if (query.length >= MINIMUM_QUERY_LENGTH) {
        repository.searchItems(query)
    } else {
        flowOf(PagingData.empty())
    }
}

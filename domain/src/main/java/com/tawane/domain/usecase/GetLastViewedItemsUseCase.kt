package com.tawane.domain.usecase

import com.tawane.domain.model.SearchItem
import com.tawane.domain.repository.IMeliRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetLastViewedItemsUseCase @Inject
constructor(private val repository: IMeliRepository) {

    operator fun invoke(): Flow<List<SearchItem>> = repository.getLastViewedItems()
}

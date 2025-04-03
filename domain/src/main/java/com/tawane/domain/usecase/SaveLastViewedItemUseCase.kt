package com.tawane.domain.usecase

import com.tawane.domain.model.SearchItem
import com.tawane.domain.repository.IMeliRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveLastViewedItemUseCase
@Inject
constructor(private val repository: IMeliRepository) {

    operator fun invoke(item: SearchItem) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveLastViewedItem(item)
        }
    }
}

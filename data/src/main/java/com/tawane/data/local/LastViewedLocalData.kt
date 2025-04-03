package com.tawane.data.local

import com.squareup.moshi.Moshi
import com.tawane.data.local.utils.fromJsonToSearchItem
import com.tawane.data.local.utils.toLastViewedItemEntity
import com.tawane.domain.model.SearchItem
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LastViewedLocalData @Inject constructor(private val appDatabase: AppDataBase, private val moshi: Moshi) :
    ILastViewedLocalData {

    override suspend fun saveLastViewedItem(searchItem: SearchItem) {
        appDatabase.lastViewedDao.insertLastViewedItem(searchItem.toLastViewedItemEntity(moshi))
        appDatabase.lastViewedDao.deleteOldLastViewedItems()
    }

    override fun getLastViewedItems(): Flow<List<SearchItem>> =
        appDatabase.lastViewedDao.getLastViewedItems().map { list ->
            list.mapNotNull { it.searchItemJson.fromJsonToSearchItem(moshi) }
        }
}

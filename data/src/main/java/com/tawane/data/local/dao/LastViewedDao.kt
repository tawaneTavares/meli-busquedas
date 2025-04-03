package com.tawane.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tawane.data.local.entity.LastViewedItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LastViewedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLastViewedItem(lastViewedItem: LastViewedItemEntity)

    @Query("SELECT * FROM last_viewed_items ORDER BY last_viewed_date DESC LIMIT 5")
    fun getLastViewedItems(): Flow<List<LastViewedItemEntity>>

    @Query(
        "DELETE FROM last_viewed_items WHERE id " +
            "NOT IN (SELECT id FROM last_viewed_items " +
            "ORDER BY last_viewed_date DESC LIMIT 5)",
    )
    suspend fun deleteOldLastViewedItems()
}

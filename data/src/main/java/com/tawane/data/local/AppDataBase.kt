package com.tawane.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tawane.data.local.dao.LastViewedDao
import com.tawane.data.local.entity.LastViewedItemEntity

@Database(
    entities = [LastViewedItemEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDataBase : RoomDatabase() {
    abstract val lastViewedDao: LastViewedDao
}

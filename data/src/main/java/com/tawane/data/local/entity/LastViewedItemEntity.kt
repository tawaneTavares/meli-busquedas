package com.tawane.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_viewed_items")
data class LastViewedItemEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "last_viewed_date") val lastViewedDate: Long,
    @ColumnInfo(name = "search_item_json") val searchItemJson: String,
)

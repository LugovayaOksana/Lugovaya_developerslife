package com.example.developerslife.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedPost(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val section: String,
    val description: String,
    val gifURL: String,
) {
    var indexInResponse: Int = -1
}
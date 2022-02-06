package com.example.developerslife.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RandomPost(
    @PrimaryKey
    val id: Long,
    val description: String,
    val gifURL: String,
)
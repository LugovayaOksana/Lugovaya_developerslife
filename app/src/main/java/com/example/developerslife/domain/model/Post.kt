package com.example.developerslife.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val section: String,
//    val author: String?,
//    val canVote: Boolean?,
//    val commentsCount: Int,
//    val date: String?,
    val description: String,
//    val fileSize: Int?,
//    val gifSize: Int?,
    val gifURL: String,
//    val height: String?,
//    val previewURL: String?,
//    val type: String?,
//    val videoPath: String?,
//    val videoSize: Int?,
//    val videoURL: String?,
//    val votes: Int?,
//    val width: String?

)
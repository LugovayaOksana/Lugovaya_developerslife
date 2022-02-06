package com.example.developerslife.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
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

) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
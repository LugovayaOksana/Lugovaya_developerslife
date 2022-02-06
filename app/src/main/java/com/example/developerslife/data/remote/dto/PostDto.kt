package com.example.developerslife.data.remote.dto

import com.example.developerslife.domain.model.FeedPost
import com.example.developerslife.domain.model.RandomPost
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Long,
    val author: String?,
    val canVote: Boolean?,
    val commentsCount: Int?,
    val date: String?,
    val description: String,
    val fileSize: Int?,
    val gifSize: Int?,
    val gifURL: String?,
    val height: String?,
    val previewURL: String?,
    val type: String?,
    val videoPath: String?,
    val videoSize: Int?,
    val videoURL: String?,
    val votes: Int?,
    val width: String?
)

fun PostDto.toRandomPost(dbId: Long): RandomPost {
    return RandomPost(
        id = dbId,
        description= description,
        gifURL= gifURL!!
    )
}

fun PostDto.toFeedPost(section: String, index: Int) = FeedPost(
    id = this.id,
    section = section,
    description = this.description,
    gifURL = this.gifURL ?: ""
).apply {
    indexInResponse = index
}
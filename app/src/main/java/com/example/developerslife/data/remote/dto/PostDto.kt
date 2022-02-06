package com.example.developerslife.data.remote.dto

import com.example.developerslife.domain.model.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val author: String?,
    val canVote: Boolean?,
    val commentsCount: Int?,
    val date: String?,
    val description: String?,
    val fileSize: Int?,
    val gifSize: Int?,
    val gifURL: String?,
    val height: String?,
    val id: Int?,
    val previewURL: String?,
    val type: String?,
    val videoPath: String?,
    val videoSize: Int?,
    val videoURL: String?,
    val votes: Int?,
    val width: String?
)

fun PostDto.toRandomPost(dbId: Long): Post {
    return Post(
        id = dbId,
        section = "random",
        description= description!!,
        gifURL= gifURL!!
    )
}
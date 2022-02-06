package com.example.developerslife.data.remote

import com.example.developerslife.data.remote.dto.PostDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("/random?json=true")
    suspend fun getRandomPost(): PostDto

    @GET("/{section}/{page}?json=true")
    suspend fun getPostsPage(
        @Path("section") section: String,
        @Path("page") page: Int): PageEnvelope

    @Serializable
    data class PageEnvelope(@SerialName("result") val items: List<PostDto>)
}
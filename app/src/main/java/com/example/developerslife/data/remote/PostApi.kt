package com.example.developerslife.data.remote

import com.example.developerslife.data.remote.dto.PostDto
import retrofit2.http.GET

interface PostApi {

    @GET("/random?json=true")
    suspend fun getDevLifePost(): PostDto

}
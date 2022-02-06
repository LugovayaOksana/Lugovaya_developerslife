package com.example.developerslife.domain.repository

import com.example.developerslife.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface DevLifePostRepository {
    suspend fun getPost(): Post
    fun getPosts(): Flow<List<Post>>
}
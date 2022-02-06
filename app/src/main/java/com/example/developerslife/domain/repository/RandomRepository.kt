package com.example.developerslife.domain.repository

import com.example.developerslife.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface RandomRepository {
    suspend fun getPost(id: Long): Post
}
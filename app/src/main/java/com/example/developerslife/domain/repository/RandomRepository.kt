package com.example.developerslife.domain.repository

import com.example.developerslife.domain.model.RandomPost

interface RandomRepository {
    suspend fun getPost(id: Long): RandomPost
}
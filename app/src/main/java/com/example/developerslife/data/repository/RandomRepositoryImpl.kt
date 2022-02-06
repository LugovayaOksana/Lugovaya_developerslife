package com.example.developerslife.data.repository

import com.example.developerslife.data.data_source.PostDao
import com.example.developerslife.data.remote.PostApi
import com.example.developerslife.data.remote.dto.toRandomPost
import com.example.developerslife.domain.model.Post
import com.example.developerslife.domain.repository.RandomRepository

class RandomRepositoryImpl(
    private val api: PostApi,
    private val dao: PostDao
): RandomRepository {

    override suspend fun getPost(id: Long): Post {
        val postFromDB = dao.getPostById(id)
        return if (postFromDB == null) {
            val postFromApi = api.getRandomPost().toRandomPost(id)
            dao.insert(postFromApi)
            postFromApi
        } else {
            postFromDB
        }
    }
}
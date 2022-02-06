package com.example.developerslife.data.repository

import com.example.developerslife.data.data_source.PostDataBase
import com.example.developerslife.data.data_source.RandomPostDao
import com.example.developerslife.data.remote.PostApi
import com.example.developerslife.data.remote.dto.toRandomPost
import com.example.developerslife.domain.model.RandomPost
import com.example.developerslife.domain.repository.RandomRepository

class RandomRepositoryImpl(
    private val api: PostApi,
    private val db: PostDataBase
): RandomRepository {

    private val dao = db.randomPostDao

    override suspend fun getPost(id: Long): RandomPost {
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
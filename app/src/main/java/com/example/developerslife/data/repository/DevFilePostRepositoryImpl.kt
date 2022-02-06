package com.example.developerslife.data.repository

import com.example.developerslife.data.data_source.PostDao
import com.example.developerslife.data.remote.PostApi
import com.example.developerslife.data.remote.dto.toDevLifePost
import com.example.developerslife.domain.model.Post
import com.example.developerslife.domain.repository.DevLifePostRepository
import kotlinx.coroutines.flow.Flow

class DevFilePostRepositoryImpl(
    private val api: PostApi,
    private val dao: PostDao
): DevLifePostRepository {

    override suspend fun getPost(): Post {
        return api.getDevLifePost().toDevLifePost()
    }

    override fun getPosts(): Flow<List<Post>> = dao.getPosts()
}
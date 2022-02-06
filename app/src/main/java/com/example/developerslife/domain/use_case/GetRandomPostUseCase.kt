package com.example.developerslife.domain.use_case

import com.example.developerslife.common.Resource
import com.example.developerslife.data.data_source.PostDao
import com.example.developerslife.domain.model.Post
import com.example.developerslife.domain.repository.RandomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetRandomPostUseCase(
    private val repository: RandomRepository
) {
    suspend operator fun invoke(id: Long): Post = repository.getPost(id)
}
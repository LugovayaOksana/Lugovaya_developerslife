package com.example.developerslife.domain.use_case

import com.example.developerslife.domain.model.RandomPost
import com.example.developerslife.domain.repository.RandomRepository

class GetRandomPostUseCase(
    private val repository: RandomRepository
) {
    suspend operator fun invoke(id: Long): RandomPost = repository.getPost(id)
}
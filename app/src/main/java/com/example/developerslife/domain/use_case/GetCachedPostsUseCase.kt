package com.example.developerslife.domain.use_case

import com.example.developerslife.common.Resource
import com.example.developerslife.data.remote.dto.toDevLifePost
import com.example.developerslife.domain.model.Post
import com.example.developerslife.domain.repository.DevLifePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCachedPostsUseCase(
    private val repository:  DevLifePostRepository
) {
    operator fun invoke(): Flow<List<Post>> = repository.getPosts()/*flow {
        try {
            emit(Resource.Loading())
            val posts = repository.getPosts()
            emit(Resource.Success(posts))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }*/
}
package com.example.developerslife.domain.use_case

import com.example.developerslife.domain.model.Post
import com.example.developerslife.domain.repository.RandomRepository
import kotlinx.coroutines.flow.Flow

class GetCachedPostsUseCase(
    private val repository:  RandomRepository
) {
    operator fun invoke(): Flow<List<Post>> = TODO()/*repository.getPosts()*//*flow {
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
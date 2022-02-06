package com.example.developerslife.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.developerslife.domain.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getPosts(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPostById(id: Int): Post?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post)
}
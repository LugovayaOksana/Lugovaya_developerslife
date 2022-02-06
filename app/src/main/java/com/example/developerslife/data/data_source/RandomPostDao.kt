package com.example.developerslife.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.developerslife.domain.model.RandomPost
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomPostDao {
    @Query("SELECT * FROM randompost WHERE id = :id")
    suspend fun getPostById(id: Long): RandomPost?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: RandomPost)
}
package com.example.developerslife.data.data_source

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.developerslife.domain.model.FeedPost

@Dao
interface FeedPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts : List<FeedPost>)

    @Query("SELECT * FROM feedpost WHERE section = :section ORDER BY indexInResponse ASC")
    fun postsBySection(section : String) : PagingSource<Int, FeedPost>

    @Query("DELETE FROM feedpost WHERE section = :section")
    fun deleteBySection(section: String)

    @Query("SELECT MAX(indexInResponse) + 1 FROM feedpost WHERE section = :section")
    fun getNextIndexInSection(section: String) : Int
}
package com.example.developerslife.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.developerslife.domain.model.FeedPost
import com.example.developerslife.domain.model.RandomPost

@Database(
    entities = [RandomPost::class, FeedPost::class],
    version = 1
)
abstract class PostDataBase: RoomDatabase() {

    abstract val randomPostDao: RandomPostDao
    abstract val feedPostDao: FeedPostDao

    companion object {
        const val DATABASE_NAME = "posts_db"
    }

}
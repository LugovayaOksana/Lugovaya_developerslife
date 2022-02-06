package com.example.developerslife.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.developerslife.domain.model.Post

@Database(
    entities = [Post::class],
    version = 1
)
abstract class PostDataBase: RoomDatabase() {

    abstract val postDao: PostDao

    companion object {
        const val DATABASE_NAME = "posts_db"
    }

}
package com.example.developerslife.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.developerslife.domain.model.Post

class FeedPostsSource(private val repository: FeedsRepository): PagingSource<Int, Post>() {

    override fun getRefreshKey(state: PagingState<Int, Post>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        TODO()
    }
}
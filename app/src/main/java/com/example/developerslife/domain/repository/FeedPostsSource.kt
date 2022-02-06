package com.example.developerslife.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.developerslife.domain.model.RandomPost

class FeedPostsSource(private val repository: FeedsRepository): PagingSource<Int, RandomPost>() {

    override fun getRefreshKey(state: PagingState<Int, RandomPost>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RandomPost> {
        TODO()
    }
}
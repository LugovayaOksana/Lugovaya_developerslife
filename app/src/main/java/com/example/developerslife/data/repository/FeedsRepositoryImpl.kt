package com.example.developerslife.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.developerslife.data.data_source.PostDataBase
import com.example.developerslife.data.remote.PostApi
import com.example.developerslife.domain.repository.FeedsRepository

private const val PAGE_SIZE = 5

class FeedsRepositoryImpl(
    private val api: PostApi,
    private val db: PostDataBase
): FeedsRepository {

    private val dao = db.feedPostDao

    @OptIn(ExperimentalPagingApi::class)
    override fun getPostBySection(section: String) = Pager(
        config = PagingConfig(PAGE_SIZE),
        remoteMediator = FeedPageRemoteMediator(api, db, section, PAGE_SIZE)
    ) {
        dao.postsBySection(section)
    }.flow
}
package com.example.developerslife.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.developerslife.domain.model.FeedPost
import kotlinx.coroutines.flow.Flow

interface FeedsRepository {
    fun getPostBySection(section: String): Flow<PagingData<FeedPost>>
}
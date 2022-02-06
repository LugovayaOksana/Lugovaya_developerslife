package com.example.developerslife.presentation.feeds

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.developerslife.domain.model.FeedPost
import com.example.developerslife.domain.model.RandomPost
import com.example.developerslife.domain.repository.FeedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class FeedState(
    val title: String,
    val posts: Flow<PagingData<FeedPost>>
    )

class FeedsViewModel(
    private val repository: FeedsRepository
): ViewModel() {

    val feedsState = MutableStateFlow<List<FeedState>>(listOf())

    init {
        feedsState.value = listOf(
            FeedState("Latest", repository.getPostBySection("latest"))
        )
    }
}
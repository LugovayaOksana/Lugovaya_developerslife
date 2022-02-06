package com.example.developerslife.presentation

import com.example.developerslife.domain.model.Post

sealed class LatestPostState {
    object NotLoading: LatestPostState()
    object Loading: LatestPostState()
    data class Error(val msg: String): LatestPostState()
}

data class DevLifeState(val data: List<Post>, val latestPostState: LatestPostState)
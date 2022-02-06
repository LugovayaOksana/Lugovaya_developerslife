package com.example.developerslife.presentation.feeds

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.developerslife.domain.model.Post
import kotlinx.coroutines.flow.MutableStateFlow

data class FeedState(
    val title: String,
    val posts: List<Post>
    )

class FeedsViewModel(): ViewModel() {

    val feedsState = MutableStateFlow<List<FeedState>>(listOf())

    init {
        val values = mutableListOf<FeedState>()
        values.add(FeedState("Latest", listOf(
            Post(1, "", "Desc 1", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif"),
            Post(2, "", "Desc 2", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif"),
            Post(3, "", "Desc 3", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif")
        )))

        values.add(FeedState("Hot", listOf(
            Post(1, "", "Desc 1", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif"),
            Post(2, "", "Desc 2", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif"),
            Post(3, "", "Desc 3", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif")
        )))

        values.add(FeedState("Top", listOf(
            Post(1, "", "Desc 1", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif"),
            Post(2, "", "Desc 2", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif"),
            Post(3, "", "Desc 3", "http://static.devli.ru/public/images/gifs/201310/07cca9b0-8985-47a5-9300-fc9331bc777a.gif")
        )))

        feedsState.value = values
    }
}
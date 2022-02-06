package com.example.developerslife.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developerslife.domain.use_case.GetCachedPostsUseCase
import com.example.developerslife.domain.use_case.GetPostUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCachedPostsUseCase: GetCachedPostsUseCase,
    private val getLatestPostsUseCase: GetPostUseCase
): ViewModel() {

    val sss = MutableStateFlow<LatestPostState>(LatestPostState.NotLoading)
    val state: Flow<DevLifeState> = combine(getCachedPostsUseCase(), sss) { allPosts, latestPostState ->
        DevLifeState(allPosts, LatestPostState.NotLoading)
    }

    fun loadLatestPost() {
        viewModelScope.launch {
            getLatestPostsUseCase().collect {

            }
        }
    }
}
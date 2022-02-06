package com.example.developerslife.presentation.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developerslife.domain.model.Post
import com.example.developerslife.domain.use_case.GetRandomPostUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class RandomState(val isBackEnabled: Boolean, val isForwardEnabled: Boolean) {
    object Loading: RandomState(false, false)
    class Loaded(val post: Post, isBackEnabled: Boolean) : RandomState(isBackEnabled, true)
    class Error(val message: String, isBackEnabled: Boolean) : RandomState(isBackEnabled, false)
}

class RandomViewModel(
    private val getRandomPostUseCase: GetRandomPostUseCase
) : ViewModel() {

    private var currentPostId = -1L

    private val _state = MutableStateFlow<RandomState>(RandomState.Loading)
    val state = _state.asStateFlow()

    init {
        loadNextPost()
    }

    fun loadPreviousPost() {
        currentPostId--
        loadPost()
    }

    fun loadNextPost() {
        currentPostId++
        loadPost()
    }

    private fun loadPost() {
        viewModelScope.launch {
            _state.update { RandomState.Loading }
            runCatching { getRandomPostUseCase(currentPostId) }
                .onSuccess { post ->
                    _state.update { RandomState.Loaded(post, currentPostId > 0) }

                }.onFailure { th ->
                    _state.update { RandomState.Error(th.localizedMessage, currentPostId > 0) }
                }
        }
    }
}
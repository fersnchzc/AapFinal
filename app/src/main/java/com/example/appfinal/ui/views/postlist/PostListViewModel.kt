package com.example.appfinal.ui.views.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfinal.domain.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostListViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(PostListUiState())
    val uiState: StateFlow<PostListUiState> = _uiState

    init{
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(2000)

            val fakePosts = List(15) { index ->
                Post(1, 1, "titulo del post $index", "Curpo del post $index")
            }
            _uiState.update {
                it.copy(isLoading = false, posts = fakePosts)
            }
        }
    }
}
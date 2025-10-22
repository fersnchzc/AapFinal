package com.example.appfinal.ui.views.postlist

import com.example.appfinal.domain.Post

data class PostListUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null
)
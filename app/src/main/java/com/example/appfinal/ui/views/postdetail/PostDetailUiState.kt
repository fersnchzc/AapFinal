package com.example.appfinal.ui.views.postdetail

import com.example.appfinal.domain.Post

data class PostDetailUiState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val errorMessage: String? = null
)


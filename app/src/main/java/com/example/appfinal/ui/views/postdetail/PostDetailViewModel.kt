package com.example.appfinal.ui.views.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfinal.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PostDetailViewModel(
    private val postId: Int,
    private val repository: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    init {
        loadPostDetails()
    }

    private fun loadPostDetails() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = try {
                val post = repository.getPost(postId)
                Result.success(post)
            } catch (e: Exception) {
                Result.failure(e)
            }

            result.fold(
                onSuccess = { post ->
                    _uiState.update {
                        it.copy(isLoading = false, post = post, errorMessage = null)
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message)
                    }
                }
            )
        }
    }
}

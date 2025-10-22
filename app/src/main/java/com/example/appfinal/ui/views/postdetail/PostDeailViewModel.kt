// El nombre del archivo debería ser PostDetailViewModel.kt
package com.example.appfinal.ui.views.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfinal.domain.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PostDetailViewModel(private val postId: Int) : ViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    init {
        loadPostDetails()
    }

    private fun loadPostDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1500)

            val fakePost = Post(
                id = postId,
                userId = 1,
                title = "Detalles del Post ID: $postId",
                body = "Este es el cuerpo completo y detallado del post. Mañana estos datos vendrán de una fuente real."
            )
            _uiState.update {
                it.copy(isLoading = false, post = fakePost)
            }
        }
    }
}

data class PostDetailUiState(
    val isLoading: Boolean = false,
    val post: Post? = null
)

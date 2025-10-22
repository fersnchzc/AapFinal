package com.example.appfinal.ui.views.postdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun PostDetailScreen(
    postId: Int,
    viewModel: PostDetailViewModel = koinInject { parametersOf(postId) },
) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.post != null) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = uiState.post!!.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = uiState.post!!.body, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
package com.example.appfinal.repository

import com.example.appfinal.domain.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun getPost(postId: Int): Post

    suspend fun refreshPosts()
}

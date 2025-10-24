package com.example.appfinal.network

import com.example.appfinal.data.remote.dto.PostDto

interface PostApiService {
    suspend fun getAllPosts(): List<PostDto>
    suspend fun getPostById(postId: Int): PostDto
}

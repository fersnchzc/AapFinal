package com.example.appfinal.network

import com.example.appfinal.data.remote.dto.PostDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PostApiServiceImpl(
    private val client: HttpClient
) : PostApiService {

    private companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
        const val POSTS_URL = "$BASE_URL/posts"
    }
    override suspend fun getAllPosts(): List<PostDto> {
        return client.get(POSTS_URL).body()
    }
    override suspend fun getPostById(postId: Int): PostDto {
        return client.get("$POSTS_URL/$postId").body()
    }
}

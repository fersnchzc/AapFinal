package com.example.appfinal.repository

import com.example.appfinal.data.local.PostDao
import com.example.appfinal.data.remote.dto.toDomain
import com.example.appfinal.domain.Post
import com.example.appfinal.data.toDomain
import com.example.appfinal.data.toEntity
import com.example.appfinal.network.PostApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val apiService: PostApiService,
    private val postDao: PostDao
) : PostRepository {
    override fun getPosts(): Flow<List<Post>> {
        return postDao.getAllPosts()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }
    override suspend fun refreshPosts() {
        try {
            val networkPosts = apiService.getAllPosts()
            postDao.insertAll(networkPosts.map { it.toEntity() })
        } catch (e: Exception) {
            println("Fallo al refrescar posts: ${e.message}")
            throw e
        }
    }
    override suspend fun getPost(postId: Int): Post {
        try {
            return postDao.getPostById(postId).toDomain()
        } catch (e: Exception) {
            println("Post no encontrado en caché, buscando en la red...")
            val networkPost = apiService.getPostById(postId)
            postDao.insertAll(listOf(networkPost.toEntity()))
            return networkPost.toDomain()
        }
    }
}

package com.example.appfinal.data.remote.dto

import com.example.appfinal.domain.Post
import kotlinx.serialization.Serializable


@Serializable
data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostDto.toDomain(): Post {
    return Post(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )
}

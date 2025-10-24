package com.example.appfinal.domain

import com.example.appfinal.data.local.PostEntity
import kotlinx.serialization.Serializable

@Serializable
data class Post(val id: Int,
    val userId: Int,
    val title: String,
    val body: String
){
    fun toEntity(): PostEntity {
        return PostEntity(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }
}
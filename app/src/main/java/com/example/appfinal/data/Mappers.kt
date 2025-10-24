package com.example.appfinal.data

import com.example.appfinal.data.local.PostEntity
import com.example.appfinal.data.remote.dto.PostDto
import com.example.appfinal.domain.Post

fun PostEntity.toDomain(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

fun PostDto.toEntity(): PostEntity{
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}
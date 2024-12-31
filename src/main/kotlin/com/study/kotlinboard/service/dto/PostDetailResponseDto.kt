package com.study.kotlinboard.service.dto

import com.study.kotlinboard.domain.Post
import java.time.LocalDateTime

data class PostDetailResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun Post.toDetailResponseDto() = PostDetailResponseDto(
    id = id,
    title = title,
    content = content,
    createdBy = createdBy,
    createdAt = createdAt,
)

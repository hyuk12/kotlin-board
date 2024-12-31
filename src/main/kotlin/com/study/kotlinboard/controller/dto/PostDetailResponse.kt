package com.study.kotlinboard.controller.dto

import com.study.kotlinboard.service.dto.PostDetailResponseDto
import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse> = emptyList(),
)

fun PostDetailResponseDto.toResponse() =
    PostDetailResponse(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
    )

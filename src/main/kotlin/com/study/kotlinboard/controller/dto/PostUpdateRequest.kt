package com.study.kotlinboard.controller.dto

import com.study.kotlinboard.service.dto.PostUpdateRequestDto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
)

fun PostUpdateRequest.toDto() =
    PostUpdateRequestDto(
        title = this.title,
        content = this.content,
        updatedBy = this.updatedBy,
    )

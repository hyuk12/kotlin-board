package com.study.kotlinboard.service.dto

import com.study.kotlinboard.domain.Comment
import com.study.kotlinboard.domain.Post

data class CommentCreateRequestDto(
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequestDto.toEntity(post: Post): Comment {
    return Comment(
        content = this.content,
        createdBy = this.createdBy,
        post = post,
    )
}

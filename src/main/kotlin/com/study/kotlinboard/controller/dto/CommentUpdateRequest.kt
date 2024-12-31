package com.study.kotlinboard.controller.dto

data class CommentUpdateRequest(
    val content: String,
    val updatedBy: String,
)

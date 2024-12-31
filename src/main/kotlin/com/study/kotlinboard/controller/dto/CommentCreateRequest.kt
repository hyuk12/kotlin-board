package com.study.kotlinboard.controller.dto

data class CommentCreateRequest(
    val content: String,
    val createdBy: String,
)

package com.study.kotlinboard.service.dto

data class PostSearchRequestDto(
    val title: String? = null,
    val createdBy: String? = null,
)

package com.study.kotlinboard.controller.dto

import com.study.kotlinboard.service.dto.PostSearchRequestDto
import org.springframework.web.bind.annotation.RequestParam

data class PostSearchRequest(
    @RequestParam
    val title: String?,
    @RequestParam
    val createdBy: String?,
)

fun PostSearchRequest.toDto() =
    PostSearchRequestDto(
        title = this.title,
        createdBy = this.createdBy,
    )

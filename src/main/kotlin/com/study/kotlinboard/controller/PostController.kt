package com.study.kotlinboard.controller

import com.study.kotlinboard.controller.dto.PostCreateRequest
import com.study.kotlinboard.controller.dto.PostDetailResponse
import com.study.kotlinboard.controller.dto.PostSearchRequest
import com.study.kotlinboard.controller.dto.PostSummaryResponse
import com.study.kotlinboard.controller.dto.PostUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {
    @PostMapping("/posts")
    fun createPost(
        @RequestBody post: PostCreateRequest,
    ): Long {
        return 1L
    }

    @PutMapping("/posts/{id}")
    fun updatePost(
        @RequestBody post: PostUpdateRequest,
        @PathVariable id: Long,
    ): Long {
        return 1L
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(
        @PathVariable id: Long,
    ): Long {
        return 1L
    }

    @GetMapping("/posts/{id}")
    fun getPost(
        @PathVariable id: Long,
    ): PostDetailResponse {
        return PostDetailResponse(
            id = id,
            title = "title",
            content = "content",
            createdBy = "createdBy",
            createdAt = java.time.LocalDateTime.now(),
        )
    }

    @GetMapping("/posts")
    fun getPosts(pageable: Pageable, postSearchRequest: PostSearchRequest): Page<PostSummaryResponse> {
        println("title: ${postSearchRequest.title}")
        println("createdBy: ${postSearchRequest.createdBy}")
        return Page.empty()
    }
}

package com.study.kotlinboard.controller

import com.study.kotlinboard.controller.dto.PostCreateRequest
import com.study.kotlinboard.controller.dto.PostDetailResponse
import com.study.kotlinboard.controller.dto.PostSearchRequest
import com.study.kotlinboard.controller.dto.PostSummaryResponse
import com.study.kotlinboard.controller.dto.PostUpdateRequest
import com.study.kotlinboard.controller.dto.toDto
import com.study.kotlinboard.domain.toDetailResponse
import com.study.kotlinboard.domain.toSummaryResponse
import com.study.kotlinboard.exception.PostListNotFoundException
import com.study.kotlinboard.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    private val postService: PostService,
) {
    @PostMapping("/posts")
    fun createPost(
        @RequestBody post: PostCreateRequest,
    ): Long {
        return postService.createPost(post.toDto())
    }

    @PutMapping("/posts/{id}")
    fun updatePost(
        @RequestBody post: PostUpdateRequest,
        @PathVariable id: Long,
    ): Long {
        return postService.updatePost(id, post.toDto())
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(
        @PathVariable id: Long,
        @RequestParam deletedBy: String,
    ): Long {
        return postService.deletePost(id, deletedBy)
    }

    @GetMapping("/posts/{id}")
    fun getPost(
        @PathVariable id: Long,
    ): PostDetailResponse {
        return postService.readPost(id).toDetailResponse()
    }

    @GetMapping("/posts")
    fun getPosts(pageable: Pageable, postSearchRequest: PostSearchRequest): Page<PostSummaryResponse> {
        println("title: ${postSearchRequest.title}")
        println("createdBy: ${postSearchRequest.createdBy}")
        return postService.readPosts(pageable, postSearchRequest.toDto())?.map { it.toSummaryResponse() }
            ?: throw PostListNotFoundException()
    }
}

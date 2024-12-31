package com.study.kotlinboard.service

import com.study.kotlinboard.controller.dto.PostDetailResponse
import com.study.kotlinboard.domain.Post
import com.study.kotlinboard.exception.PostNotDeletableException
import com.study.kotlinboard.exception.PostNotFoundException
import com.study.kotlinboard.repository.PostRepository
import com.study.kotlinboard.service.dto.PostCreateRequestDto
import com.study.kotlinboard.service.dto.PostDetailResponseDto
import com.study.kotlinboard.service.dto.PostSearchRequestDto
import com.study.kotlinboard.service.dto.PostSummaryResponseDto
import com.study.kotlinboard.service.dto.PostUpdateRequestDto
import com.study.kotlinboard.service.dto.toDetailResponseDto
import com.study.kotlinboard.service.dto.toEntity
import com.study.kotlinboard.service.dto.toSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createPost(postCreateRequestDto: PostCreateRequestDto): Long {
        return postRepository.save(postCreateRequestDto.toEntity()).id
    }

    @Transactional
    fun updatePost(id: Long, postUpdateRequestDto: PostUpdateRequestDto): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        post.update(postUpdateRequestDto)
        return id
    }

    @Transactional
    fun deletePost(id: Long, deletedBy: String): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        if (post.createdBy != deletedBy) throw PostNotDeletableException()
        postRepository.delete(post)
        return id
    }

    fun readPost(id: Long): Post {
        return postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
    }

    fun readPosts(pageable: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>? {
        return postSearchRequestDto.title?.let { postRepository.findAllByTitleOrderByCreatedAt(pageable, it) }
    }

    fun getPost(id: Long): PostDetailResponseDto {
        return postRepository.findByIdOrNull(id)?.toDetailResponseDto() ?: throw PostNotFoundException()
    }

    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<PostSummaryResponseDto> {
        return postRepository.findPageBy(pageRequest, postSearchRequestDto).toSummaryResponseDto()
    }
}

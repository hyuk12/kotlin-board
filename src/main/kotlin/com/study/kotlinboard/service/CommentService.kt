package com.study.kotlinboard.service

import com.study.kotlinboard.exception.PostNotFoundException
import com.study.kotlinboard.repository.CommentRepository
import com.study.kotlinboard.repository.PostRepository
import com.study.kotlinboard.service.dto.CommentCreateRequestDto
import com.study.kotlinboard.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createComment(postId: Long, commentCreateRequestDto: CommentCreateRequestDto): Long? {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return commentRepository.save(
            commentCreateRequestDto.toEntity(post),
        ).id
    }
}

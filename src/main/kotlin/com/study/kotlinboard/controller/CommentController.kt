package com.study.kotlinboard.controller

import com.study.kotlinboard.controller.dto.CommentCreateRequest
import com.study.kotlinboard.controller.dto.CommentUpdateRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController {
    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody comment: CommentCreateRequest,
    ): Long {
        println(comment.content)
        println(comment.createdBy)
        return 1L
    }

    @PutMapping("/comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody comment: CommentUpdateRequest,
    ): Long {
        println(comment.content)
        println(comment.updatedBy)
        return 1L
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
        @RequestParam deletedBy: String,
    ): Long {
        println(deletedBy)
        return 1L
    }
}

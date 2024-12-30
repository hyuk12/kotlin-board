package com.study.kotlinboard.domain

import com.study.kotlinboard.controller.dto.PostDetailResponse
import com.study.kotlinboard.controller.dto.PostSummaryResponse
import com.study.kotlinboard.exception.PostNotUpdatableException
import com.study.kotlinboard.service.dto.PostUpdateRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Post(
    createdBy: String,
    title: String,
    content: String,
) : BaseEntity(createdBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        protected set
    var content: String = content
        protected set

    fun update(postUpdateRequestDto: PostUpdateRequestDto) {
        if (this.createdBy != postUpdateRequestDto.updatedBy) {
            throw PostNotUpdatableException()
        }
        this.title = postUpdateRequestDto.title
        this.content = postUpdateRequestDto.content
        super.updatedBy(postUpdateRequestDto.updatedBy)
    }
}

fun Post.toDetailResponse() =
    PostDetailResponse(
        id = this.id,
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
    )

fun Post.toSummaryResponse() =
    PostSummaryResponse(
        id = this.id,
        title = this.title,
        createdBy = this.createdBy,
        createdAt = this.createdAt.toString(),
    )

package com.study.kotlinboard.repository

import com.study.kotlinboard.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByTitleOrderByCreatedAt(pageable: Pageable, title: String): Page<Post>
}

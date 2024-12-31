package com.study.kotlinboard.repository

import com.study.kotlinboard.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}

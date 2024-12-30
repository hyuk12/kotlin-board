package com.study.kotlinboard.repository

import com.study.kotlinboard.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>

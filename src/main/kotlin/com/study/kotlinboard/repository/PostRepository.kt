package com.study.kotlinboard.repository

import com.study.kotlinboard.domain.Post
import com.study.kotlinboard.domain.QPost.post
import com.study.kotlinboard.service.dto.PostSearchRequestDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository {
    fun findAllByTitleOrderByCreatedAt(pageable: Pageable, title: String): Page<Post>
}

interface CustomPostRepository {
    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class CustomPostRepositoryImpl : CustomPostRepository, QuerydslRepositorySupport(Post::class.java) {
    override fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post> {
        val result = from(post)
            .where(
                postSearchRequestDto.title?.let { post.title.contains(it) },
                postSearchRequestDto.createdBy?.let { post.createdBy.eq(it) },
            )
            .orderBy(post.createdAt.desc())
            .limit(pageRequest.pageSize.toLong())
            .offset(pageRequest.offset)
            .fetchResults()

        return PageImpl(result.results, pageRequest, result.total)
    }
}

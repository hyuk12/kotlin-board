package com.study.kotlinboard.service

import com.study.kotlinboard.domain.Post
import com.study.kotlinboard.exception.PostNotFoundException
import com.study.kotlinboard.repository.CommentRepository
import com.study.kotlinboard.repository.PostRepository
import com.study.kotlinboard.service.dto.CommentCreateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
        given("댓글 생성 시") {
            postRepository.save(
                Post(
                    title = "제목",
                    content = "내용",
                    createdBy = "작성자",
                ),
            )
            When("인풋이 정상적으로 들어오면") {
                val commentId = commentService.createComment(
                    1L,
                    CommentCreateRequestDto(
                        content = "댓글 내용",
                        createdBy = "작성자",
                    ),
                )
                then("정상 생성됨을 확인한다.") {
                    commentId!! shouldBeGreaterThan 0L
                    val comment = commentRepository.findByIdOrNull(commentId)
                    comment shouldNotBe null
                    comment?.content shouldBe "댓글 내용"
                    comment?.createdBy shouldBe "작성자"
                }
            }
            When("게시글이 존재하지 않으면") {
                then("게시글이 존재하지 않음 예외가 발생한다.") {
                    shouldThrow<PostNotFoundException> {
                        commentService.createComment(
                            9999L,
                            CommentCreateRequestDto(
                                content = "댓글 내용",
                                createdBy = "작성자",
                            ),
                        )
                    }
                }
            }
        }
    })

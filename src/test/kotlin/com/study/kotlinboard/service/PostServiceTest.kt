package com.study.kotlinboard.service

import com.study.kotlinboard.domain.Post
import com.study.kotlinboard.exception.PostNotDeletableException
import com.study.kotlinboard.exception.PostNotFoundException
import com.study.kotlinboard.exception.PostNotUpdatableException
import com.study.kotlinboard.repository.PostRepository
import com.study.kotlinboard.service.dto.PostCreateRequestDto
import com.study.kotlinboard.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
) : BehaviorSpec({
        given("게시글 생성 시") {
            When("게시글 인풋이 정상적으로 들어오면") {
                val postId = postService.createPost(
                    PostCreateRequestDto(
                        title = "제목",
                        content = "내용",
                        createdBy = "작성자",
                    ),
                )
                then("게시글이 정상적으로 생성됨을 확인한다.") {
                    postId shouldBeGreaterThan 0L
                    val post = postRepository.findByIdOrNull(postId)
                    post shouldNotBe null
                    post?.title shouldBe "제목"
                    post?.content shouldBe "내용"
                    post?.createdBy shouldBe "작성자"
                }
            }
        }
        given("게시글 수정 시") {
            val saved = postRepository.save(
                Post(
                    title = "제목",
                    content = "내용",
                    createdBy = "작성자",
                ),
            )
            When("정상 수정 시") {
                val updatedId = postService.updatePost(
                    saved.id,
                    PostUpdateRequestDto(
                        title = "수정된 제목",
                        content = "수정된 내용",
                        updatedBy = "작성자",
                    ),
                )
                then("게시글이 정상적으로 수정됨을 확인한다.") {
                    saved.id shouldBe updatedId
                    val updated = postRepository.findByIdOrNull(updatedId)
                    updated?.title shouldBe "수정된 제목"
                    updated?.content shouldBe "수정된 내용"
                }
            }
            When("게시글이 없을 때") {
                then("게시글을 찾을 수 없다라는 예외가 발생한다.") {
                    shouldThrow<PostNotFoundException> {
                        postService.updatePost(
                            9999L,
                            PostUpdateRequestDto(
                                title = "수정된 제목",
                                content = "수정된 내용",
                                updatedBy = "수정된 작성자",
                            ),
                        )
                    }
                }
            }
            When("작성자가 동일하지 않으면") {
                then("수정할 수 없는 게시물 입니다. 예외가 발생한다.") {
                    shouldThrow<PostNotUpdatableException> {
                        postService.updatePost(
                            1L,
                            PostUpdateRequestDto(
                                title = "수정된 제목",
                                content = "수정된 내용",
                                updatedBy = "수정된 작성자",
                            ),
                        )
                    }
                }
            }
        }
        given("게시글 삭제 시") {
            val saved = postRepository.save(
                Post(
                    title = "제목",
                    content = "내용",
                    createdBy = "작성자",
                ),
            )
            When("정상 삭제 시") {
                val postId = postService.deletePost(saved.id, "작성자")
                then("게시글이 정상적으로 삭제됨을 확인한다.") {
                    postId shouldBe saved.id
                    postRepository.findByIdOrNull(postId) shouldBe null
                }
            }
            When("작성자가 동일하지 않으면") {
                val saved2 = postRepository.save(
                    Post(
                        title = "제목",
                        content = "내용",
                        createdBy = "작성자",
                    ),
                )
                then("삭제할 수 없는 게시물입니다. 예외가 발생한다.") {
                    shouldThrow<PostNotDeletableException> { postService.deletePost(saved2.id, "삭제자") }
                }
            }
        }
    })

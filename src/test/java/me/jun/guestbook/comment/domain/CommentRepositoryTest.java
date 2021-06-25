package me.jun.guestbook.comment.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Transactional
    void findAllByWriterIdTest() {
        for (long postId = 0; postId < 20; postId++) {
            Comment comment = Comment.builder()
                    .postId(1L)
                    .content(postId + "content")
                    .build();

            commentRepository.save(comment);
        }

        Page<Comment> comments = commentRepository.findAllByPostId(1L, PageRequest.of(0, 10));

        assertAll(() -> assertThat(comments.getTotalPages()).isEqualTo(2),
                () -> assertThat(comments.getTotalElements()).isEqualTo(20),
                () -> assertThat(comments.getSize()).isEqualTo(10)
        );
    }

    @Test
    void deleteCommentByPostId() {
        Comment comment = Comment.builder()
                .id(1L)
                .writerId(1L)
                .postId(1L)
                .content("test content")
                .build();

        commentRepository.save(comment);

        commentRepository.deleteByPostId(1L);

        assertThat(commentRepository.findById(1L))
                .isEmpty();
    }
}
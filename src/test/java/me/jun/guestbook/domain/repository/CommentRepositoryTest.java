package me.jun.guestbook.domain.repository;

import me.jun.guestbook.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static me.jun.guestbook.CommentFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Transactional
    void findAllByWriterIdTest() {
        for (int id = 1; id <= 20; id++) {
            Comment comment = Comment.builder()
                    .postId(POST_ID)
                    .content(CONTENT)
                    .build();

            commentRepository.save(comment);
        }

        Page<Comment> comments = commentRepository.findAllByPostId(POST_ID, PageRequest.of(0, 10));

        assertAll(() -> assertThat(comments.getTotalPages()).isEqualTo(2),
                () -> assertThat(comments.getTotalElements()).isEqualTo(20),
                () -> assertThat(comments.getSize()).isEqualTo(10)
        );
    }

    @Test
    void deleteCommentByPostIdTest() {
        commentRepository.save(comment());

        commentRepository.deleteByPostId(POST_ID);

        assertThat(commentRepository.findById(COMMENT_ID))
                .isEmpty();
    }

    @Test
    @Rollback(value = false)
    void deleteAllCommentByWriterIdTest() {
        commentRepository.save(comment());
        commentRepository.save(comment().toBuilder().id(2L).build());
        commentRepository.save(comment().toBuilder().id(3L).build());

        commentRepository.deleteAllByCommentWriter(commentWriter());

        assertAll(
                () -> assertThat(commentRepository.findById(1L)).isEmpty(),
                () -> assertThat(commentRepository.findById(2L)).isEmpty(),
                () -> assertThat(commentRepository.findById(3L)).isEmpty()
        );
    }
}
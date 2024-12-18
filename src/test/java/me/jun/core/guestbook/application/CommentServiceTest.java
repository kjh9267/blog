package me.jun.core.guestbook.application;

import me.jun.core.guestbook.application.dto.CommentResponse;
import me.jun.core.guestbook.application.dto.CommentUpdateRequest;
import me.jun.core.guestbook.application.dto.PagedCommentsResponse;
import me.jun.core.guestbook.application.exception.CommentNotFoundException;
import me.jun.core.guestbook.domain.exception.CommentWriterMismatchException;
import me.jun.core.guestbook.domain.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static me.jun.core.guestbook.CommentFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository);
    }

    @Test
    void createCommentTest() {
        given(commentRepository.save(any()))
                .willReturn(comment());

        assertThat(commentService.createComment(commentCreateRequest()))
                .isEqualToComparingFieldByField(commentResponse());
    }

    @Test
    void retrieveCommentTest() {
        given(commentRepository.findById(COMMENT_ID))
                .willReturn(Optional.of(comment()));

        assertAll(
                () -> assertThat(commentService.retrieveComment(COMMENT_ID))
                .isEqualToComparingFieldByField(commentResponse()),
                () -> assertThat(commentService.retrieveComment(COMMENT_ID))
                .isInstanceOf(CommentResponse.class)
        );
    }

    @Test
    void retrieveCommentFailTest() {
        given(commentRepository.findById(COMMENT_ID))
                .willReturn(Optional.empty());

        assertThrows(
                CommentNotFoundException.class,
                () -> assertThat(commentService.retrieveComment(COMMENT_ID))
        );
    }

    @Test
    void updateCommentTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment()));

        given(commentRepository.save(any()))
                .willReturn(comment());

        assertThat(commentService.updateComment(commentUpdateRequest()))
                .isEqualToComparingFieldByField(commentResponse());
    }

    @Test
    void noComment_updateCommentFailTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class,
                () -> commentService.updateComment(commentUpdateRequest()));
    }

    @Test
    void commentWriterMismatch_updateCommentFailTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment()));

        CommentUpdateRequest request = commentUpdateRequest().toBuilder()
                .writerId(2L)
                .build();

        assertThrows(
                CommentWriterMismatchException.class,
                () -> commentService.updateComment(request));
    }

    @Test
    void deleteCommentTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment()));

        commentService.deleteComment(COMMENT_ID, COMMENT_WRITER_ID);

        verify(commentRepository).deleteById(any());
    }

    @Test
    void noComment_deleteCommentFailTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class,
                () -> commentService.deleteComment(COMMENT_ID, COMMENT_WRITER_ID));
    }

    @Test
    void writerMismatch_deleteCommentFailTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment()));

        assertThrows(CommentWriterMismatchException.class,
                () -> commentService.deleteComment(COMMENT_ID, 2L));
    }

    @Test
    void deleteCommentByPostIdTest() {
        doNothing().when(commentRepository)
                .deleteByPostId(any());

        commentService.deleteCommentByPostId(POST_ID);

        verify(commentRepository).deleteByPostId(any());
    }

    @Test
    void deleteCommentByWriterIdTest() {
        doNothing().when(commentRepository)
                .deleteAllByCommentWriter(any());

        commentService.deleteCommentByWriterId(COMMENT_WRITER_ID);

        verify(commentRepository).deleteAllByCommentWriter(commentWriter());
    }

    @Test
    void queryCommentsByPostId() {
        given(commentRepository.findAllByPostId(any(), any()))
                .willReturn(Page.empty());

        assertThat(commentService.queryCommentsByPostId(POST_ID, PageRequest.of(1, 10)))
                .isInstanceOf(PagedCommentsResponse.class);
    }
}
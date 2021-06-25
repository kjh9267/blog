package me.jun.guestbook.comment.application;

import me.jun.guestbook.comment.application.exception.CommentNotFoundException;
import me.jun.guestbook.comment.application.exception.CommentWriterMismatchException;
import me.jun.guestbook.comment.domain.Comment;
import me.jun.guestbook.comment.domain.CommentRepository;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.comment.presentation.dto.CommentUpdateRequest;
import me.jun.guestbook.comment.presentation.dto.PagedCommentsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    private Comment comment;

    private CommentResponse commentResponse;

    private CommentUpdateRequest commentUpdateRequest;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository);

        comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .writerId(1L)
                .content("test")
                .build();

        commentResponse = CommentResponse.builder()
                .id(1L)
                .postId(1L)
                .writerId(1L)
                .content("test")
                .build();

        commentUpdateRequest = CommentUpdateRequest.builder()
                .id(1L)
                .postId(1L)
                .content("test")
                .build();
    }

    @Test
    void createCommentTest() {
        CommentCreateRequest request = CommentCreateRequest.builder()
                .postId(1L)
                .content("test")
                .build();

        given(commentRepository.save(any()))
                .willReturn(comment);

        assertThat(commentService.createComment(request, 1L))
                .isEqualToComparingFieldByField(commentResponse);
    }

    @Test
    void retrieveCommentTest() {
        given(commentRepository.findById(1L))
                .willReturn(Optional.of(comment));

        assertThat(commentService.retrieveComment(1L))
                .isEqualToComparingFieldByField(commentResponse);
    }

    @Test
    void retrieveCommentFailTest() {
        given(commentRepository.findById(1L))
                .willReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class,
                () -> assertThat(commentService.retrieveComment(1L)));
    }

    @Test
    void updateCommentTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment));

        given(commentRepository.save(any()))
                .willReturn(comment);

        assertThat(commentService.updateComment(commentUpdateRequest, 1L))
                .isEqualToComparingFieldByField(commentResponse);
    }

    @Test
    void noComment_updateCommentFailTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class,
                () -> commentService.updateComment(commentUpdateRequest, 1L));
    }

    @Test
    void commentWriterMismatch_updateCommentFailTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment));

        assertThrows(CommentWriterMismatchException.class,
                () -> commentService.updateComment(commentUpdateRequest, 2L));
    }

    @Test
    void deleteCommentTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment));

        commentService.deleteComment(1L, 1L);

        verify(commentRepository).deleteById(any());
    }

    @Test
    void noComment_deleteCommentFailTest() {
        assertThrows(CommentNotFoundException.class,
                () -> commentService.deleteComment(1L, 1L));
    }

    @Test
    void writerMismatch_deleteCommentFailTest() {
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(comment));

        assertThrows(CommentWriterMismatchException.class,
                () -> commentService.deleteComment(1L, 2L));
    }

    @Test
    void deleteCommentByPostIdTest() {
        doNothing().when(commentRepository)
                .deleteByPostId(any());

        commentService.deleteCommentByPostId(1L);

        verify(commentRepository).deleteByPostId(any());
    }

    @Test
    void deleteCommentByWriterIdTest() {
        doNothing().when(commentRepository)
                .deleteByWriterId(any());

        commentService.deleteCommentByWriterId(1L);

        verify(commentRepository).deleteByWriterId(1L);
    }

    @Test
    void queryCommentsByPostId() {
        given(commentRepository.findAllByPostId(any(), any()))
                .willReturn(Page.empty());

        assertThat(commentService.queryCommentsByPostId(1L, PageRequest.of(1, 10)))
                .isInstanceOf(PagedCommentsResponse.class);
    }
}
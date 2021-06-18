package me.jun.guestbook.comment.application;

import me.jun.guestbook.comment.domain.Comment;
import me.jun.guestbook.comment.domain.CommentRepository;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    private Comment comment;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository);

        comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .writerId(1L)
                .content("test")
                .build();
    }

    @Test
    void createCommentTest() {
        CommentCreateRequest request = CommentCreateRequest.builder()
                .postId(1L)
                .content("test")
                .build();

        CommentResponse commentResponse = CommentResponse.builder()
                .id(1L)
                .postId(1L)
                .writerId(1L)
                .content("test")
                .build();

        given(commentRepository.save(any()))
                .willReturn(comment);

        assertThat(commentService.createComment(request, 1L))
                .isEqualToComparingFieldByField(commentResponse);
    }
}
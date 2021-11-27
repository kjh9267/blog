package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.dto.CommentCreateRequest;
import me.jun.guestbook.application.dto.CommentResponse;
import me.jun.guestbook.application.dto.CommentUpdateRequest;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import me.jun.guestbook.application.exception.CommentNotFoundException;
import me.jun.guestbook.domain.Comment;
import me.jun.guestbook.domain.CommentWriter;
import me.jun.guestbook.domain.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    @Async
    public CompletableFuture<CommentResponse> createComment(CommentCreateRequest request, Long writerId) {
        Comment comment = request.toEntity()
                .updateCommentWriter(new CommentWriter(writerId));

        comment = commentRepository.save(comment);

        return CompletableFuture.completedFuture(
                CommentResponse.from(comment)
        );
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<CommentResponse> retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        return CompletableFuture.completedFuture(
                CommentResponse.from(comment)
        );
    }

    @Async
    public CompletableFuture<CommentResponse> updateComment(CommentUpdateRequest request, Long writerId) {
        Comment comment = commentRepository.findById(request.getId())
                .orElseThrow(CommentNotFoundException::new);

        comment = comment.validateWriter(writerId)
                .updateContent(request.getContent());

        comment = commentRepository.save(comment);

        return CompletableFuture.completedFuture(
                CommentResponse.from(comment)
        );
    }

    @Async
    public CompletableFuture<Long> deleteComment(Long id, Long writerId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        comment.validateWriter(writerId);

        commentRepository.deleteById(id);

        return CompletableFuture.completedFuture(id);
    }

    @Async
    public CompletableFuture<Void> deleteCommentByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> deleteCommentByWriterId(Long writerId) {
        commentRepository.deleteAllByCommentWriter(new CommentWriter(writerId));
        return CompletableFuture.completedFuture(null);
    }

    public PagedCommentsResponse queryCommentsByPostId(Long postId, PageRequest pageRequest) {
        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageRequest);
        return PagedCommentsResponse.from(comments);
    }
}

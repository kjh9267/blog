package me.jun.core.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.core.guestbook.application.dto.CommentCreateRequest;
import me.jun.core.guestbook.application.dto.CommentResponse;
import me.jun.core.guestbook.application.dto.CommentUpdateRequest;
import me.jun.core.guestbook.application.dto.PagedCommentsResponse;
import me.jun.core.guestbook.application.exception.CommentNotFoundException;
import me.jun.core.guestbook.domain.Comment;
import me.jun.core.guestbook.domain.CommentWriter;
import me.jun.core.guestbook.domain.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse createComment(CommentCreateRequest request) {
        Comment comment = request.toEntity();

        comment = commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponse retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        return CommentResponse.from(comment);
    }

    public CommentResponse updateComment(CommentUpdateRequest request) {
        Comment updatedComment = commentRepository.findById(request.getId())
                .map(comment -> comment.validateWriter(request.getWriterId()))
                .map(comment -> comment.updateContent(request.getContent()))
                .orElseThrow(() -> new CommentNotFoundException(request.getId()));

        updatedComment = commentRepository.save(updatedComment);

        return CommentResponse.from(updatedComment);
    }

    public Long deleteComment(Long id, Long writerId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        comment.validateWriter(writerId);

        commentRepository.deleteById(id);

        return id;
    }

    public void deleteCommentByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
    }

    public void deleteCommentByWriterId(Long writerId) {
        commentRepository.deleteAllByCommentWriter(new CommentWriter(writerId));
    }

    public PagedCommentsResponse queryCommentsByPostId(Long postId, PageRequest pageRequest) {
        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageRequest);
        return PagedCommentsResponse.from(comments);
    }
}

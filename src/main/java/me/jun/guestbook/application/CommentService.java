package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.exception.CommentNotFoundException;
import me.jun.guestbook.domain.Comment;
import me.jun.guestbook.domain.repository.CommentRepository;
import me.jun.guestbook.domain.CommentWriter;
import me.jun.guestbook.application.dto.CommentCreateRequest;
import me.jun.guestbook.application.dto.CommentResponse;
import me.jun.guestbook.application.dto.CommentUpdateRequest;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse createComment(CommentCreateRequest request, Long writerId) {
        Comment comment = request.toEntity();
        comment.setCommentWriter(new CommentWriter(writerId));
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public CommentResponse retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        return CommentResponse.from(comment);
    }

    public CommentResponse updateComment(CommentUpdateRequest request, Long writerId) {
        Comment comment = commentRepository.findById(request.getId())
                .orElseThrow(CommentNotFoundException::new);

        comment.validateWriter(writerId);

        comment.setContent(request.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public Long deleteComment(Long id, Long writerId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

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

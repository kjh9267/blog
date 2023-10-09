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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse createComment(CommentCreateRequest request, String writerEmail) {
        Comment comment = request.toEntity()
                .updateCommentWriter(new CommentWriter(writerEmail));

        comment = commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponse retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        return CommentResponse.from(comment);
    }

    public CommentResponse updateComment(CommentUpdateRequest request, String writerEmail) {
        Comment comment = commentRepository.findById(request.getId())
                .orElseThrow(() -> new CommentNotFoundException(request.getId()));

        comment = comment.validateWriter(writerEmail)
                .updateContent(request.getContent());

        comment = commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    public Long deleteComment(Long id, String writerEmail) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        comment.validateWriter(writerEmail);

        commentRepository.deleteById(id);

        return id;
    }

    public void deleteCommentByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
    }

    public void deleteCommentByWriterEmail(String writerEmail) {
        commentRepository.deleteAllByCommentWriter(new CommentWriter(writerEmail));
    }

    public PagedCommentsResponse queryCommentsByPostId(Long postId, PageRequest pageRequest) {
        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageRequest);
        return PagedCommentsResponse.from(comments);
    }
}

package me.jun.guestbook.comment.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.domain.Comment;
import me.jun.guestbook.comment.domain.CommentRepository;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse createComment(CommentCreateRequest request, Long writerId) {
        Comment comment = request.toEntity();
        comment.setWriterId(writerId);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }
}

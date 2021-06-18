package me.jun.guestbook.comment.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.domain.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
}

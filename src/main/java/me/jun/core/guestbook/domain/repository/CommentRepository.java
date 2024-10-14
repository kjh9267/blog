package me.jun.core.guestbook.domain.repository;

import me.jun.core.guestbook.domain.Comment;
import me.jun.core.guestbook.domain.CommentWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    void deleteByPostId(Long postId);

    @Modifying(clearAutomatically = true)
    void deleteAllByCommentWriter(CommentWriter commentWriter);
}

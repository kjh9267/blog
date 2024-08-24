package me.jun.guestbook.domain.repository;

import me.jun.guestbook.domain.Comment;
import me.jun.guestbook.domain.CommentWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    void deleteByPostId(Long postId);

    @Modifying(clearAutomatically = true)
    @Query("delete from Comment c where c.commentWriter = :commentWriter")
    void deleteAllByCommentWriter(CommentWriter commentWriter);
}

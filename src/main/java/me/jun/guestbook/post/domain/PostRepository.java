package me.jun.guestbook.post.domain;

import me.jun.guestbook.comment.domain.PostWriter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByPostWriter(PostWriter postWriter);
}

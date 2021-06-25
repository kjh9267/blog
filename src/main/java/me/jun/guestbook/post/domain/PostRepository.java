package me.jun.guestbook.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByWriterId(Long writerId);
}

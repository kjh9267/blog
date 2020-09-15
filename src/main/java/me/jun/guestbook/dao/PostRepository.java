package me.jun.guestbook.dao;

import me.jun.guestbook.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

package me.jun.guestbook.domain.repository;

import me.jun.guestbook.domain.Post;
import me.jun.guestbook.domain.PostCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCountRepository extends JpaRepository<PostCount, Long> {

    Optional<PostCount> findByPostId(Long postId);
}

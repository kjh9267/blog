package me.jun.core.guestbook.domain.repository;

import me.jun.core.guestbook.domain.PostCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCountRepository extends JpaRepository<PostCount, Long> {

    Optional<PostCount> findByPostId(Long postId);
}

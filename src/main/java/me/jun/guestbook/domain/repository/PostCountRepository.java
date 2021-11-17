package me.jun.guestbook.domain.repository;

import me.jun.guestbook.domain.PostCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCount, Long> {
}

package me.jun.guestbook.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempPostRepository extends JpaRepository<TempPost, Long> {
}

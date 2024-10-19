package me.jun.core.guestbook.domain.repository;

import me.jun.core.guestbook.domain.Post;
import me.jun.core.guestbook.domain.PostWriter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @Cacheable(cacheNames = "postStore", key = "#postId")
    Optional<Post> findById(Long postId);

    @CacheEvict(cacheNames = "postStore", allEntries = true)
    @Modifying(clearAutomatically = true)
    @Query("delete from Post p where p.postWriter = :postWriter")
    void deleteAllByPostWriter(PostWriter postWriter);
}

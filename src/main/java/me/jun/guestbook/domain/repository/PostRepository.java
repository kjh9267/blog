package me.jun.guestbook.domain.repository;

import me.jun.guestbook.domain.Post;
import me.jun.guestbook.domain.PostWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from Post p where p.postWriter = :postWriter")
    void deleteAllByPostWriter(PostWriter postWriter);
}

package me.jun.guestbook;

import me.jun.guestbook.domain.Post;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static me.jun.guestbook.PostFixture.post;

@Component
public class SlowPostRepository {

    @Cacheable(cacheNames = "posts", key = "#postId")
    public Optional<Post> findById(Long postId) {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(post());
    }
}

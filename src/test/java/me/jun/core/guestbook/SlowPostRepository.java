package me.jun.core.guestbook;

import me.jun.core.guestbook.domain.Post;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SlowPostRepository {

    @Cacheable(cacheNames = "postStore", key = "#postId")
    public Optional<Post> findById(Long postId) {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(PostFixture.post());
    }
}

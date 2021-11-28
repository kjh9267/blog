package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.guestbook.application.exception.PostCountNotFoundException;
import me.jun.guestbook.domain.Hits;
import me.jun.guestbook.domain.PostCount;
import me.jun.guestbook.domain.repository.PostCountRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostCountService {

    private final PostCountRepository postCountRepository;

    @Async
    public PostCount createPostCount(Long postId) {
        PostCount postCount = PostCount.builder()
                .hits(new Hits(0L))
                .postId(postId)
                .build();

        postCount = postCountRepository.save(postCount);
        return postCount;
    }

    @Retryable(maxAttempts = Integer.MAX_VALUE)
    public Long updateHits(Long postId) {
        PostCount postCount = postCountRepository.findByPostId(postId)
                .orElseThrow(() -> new PostCountNotFoundException("post count not found"));

        postCount.updateHits();

        log.info("post count service");

        return postCount.getHits().getValue();
    }
}

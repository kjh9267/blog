package me.jun.core.guestbook.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.core.guestbook.application.exception.PostCountNotFoundException;
import me.jun.core.guestbook.domain.Hits;
import me.jun.core.guestbook.domain.PostCount;
import me.jun.core.guestbook.domain.repository.PostCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostCountService {

    private final PostCountRepository postCountRepository;

    public PostCount createPostCount(Long postId) {
        PostCount postCount = PostCount.builder()
                .hits(new Hits(0L))
                .postId(postId)
                .build();

        postCount = postCountRepository.save(postCount);
        return postCount;
    }

    public Long updateHits(Long postId) {
        PostCount updatedPostCount = postCountRepository.findByPostId(postId)
                .map(PostCount::updateHits)
                .orElseThrow(() -> new PostCountNotFoundException(postId));

        return updatedPostCount.getHits().getValue();
    }
}

package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.exception.PostCountNotFoundException;
import me.jun.guestbook.domain.Hits;
import me.jun.guestbook.domain.PostCount;
import me.jun.guestbook.domain.repository.PostCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long updateHits(Long postId) {
        PostCount postCount = postCountRepository.findByPostId(postId)
                .orElseThrow(() -> new PostCountNotFoundException("post count not found"));

        postCount.updateHits();

        return postCount.getHits().getValue();
    }
}

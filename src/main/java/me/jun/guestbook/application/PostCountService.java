package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.domain.repository.PostCountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCountService {

    private final PostCountRepository postCountRepository;

    public Long updateHits(Long postId) {
        return null;
    }
}

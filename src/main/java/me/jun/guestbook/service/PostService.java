package me.jun.guestbook.service;

import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.PostCreateDto;
import me.jun.guestbook.dto.PostRequestId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post readPost(PostRequestId postRequestId) {
        Long id = postRequestId.getId();

        return postRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);
    }

    public void createPost(PostCreateDto postCreateDto) {
        final Post post = postCreateDto.toEntity();

        postRepository.save(post);
    }
}

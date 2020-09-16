package me.jun.guestbook.service;

import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.PostCreateDto;
import me.jun.guestbook.dto.PostDeleteDto;
import me.jun.guestbook.dto.PostReadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post readPost(PostReadDto postReadDto) {
        Long id = postReadDto.getId();

        return postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void createPost(PostCreateDto postCreateDto) {
        final Post post = postCreateDto.toEntity();

        postRepository.save(post);
    }

    public void deletePost(PostDeleteDto postDeleteDto) {
        final Long id = postDeleteDto.getId();

        postRepository.deleteById(id);
    }
}

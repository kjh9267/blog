package me.jun.guestbook.service;

import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostReadDto readPost(PostReadRequestId postReadRequestId) {
        Long id = postReadRequestId.getId();

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .account(post.getAccount())
                .build();
    }

    public void createPost(PostCreateDto postCreateDto) {
        final Post post = postCreateDto.toEntity();

        postRepository.save(post);
    }

    public void deletePost(PostDeleteDto postDeleteDto) {
        final Long id = postDeleteDto.getId();

        postRepository.deleteById(id);
    }

    public PostReadDto updatePost(PostUpdateDto postUpdateDto) {
        final Post post = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(IllegalArgumentException::new);

        final String password = post.getAccount().getPassword();
        final String requestPassword = postUpdateDto.getAccount().getPassword();

        if (!requestPassword.equals(password)) {
            throw new IllegalArgumentException("wrong password");
        }

        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());

        final Post savedPost = postRepository.save(post);

        return PostReadDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .account(savedPost.getAccount())
                .build();
    }
}

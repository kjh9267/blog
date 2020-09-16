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

    public PostInfoDto readPost(PostReadRequestDto postReadRequestDto) {
        Long id = postReadRequestDto.getId();

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostInfoDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .account(post.getAccount())
                .build();
    }

    public void createPost(PostCreateRequestDto postCreateRequestDto) {
        final Post post = postCreateRequestDto.toEntity();

        postRepository.save(post);
    }

    public void deletePost(PostDeleteRequestDto postDeleteRequestDto) {
        final Long id = postDeleteRequestDto.getId();

        postRepository.deleteById(id);
    }

    public PostInfoDto updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        final Post post = postRepository.findById(postUpdateRequestDto.getId())
                .orElseThrow(IllegalArgumentException::new);

        final String password = post.getAccount().getPassword();
        final String requestPassword = postUpdateRequestDto.getAccount().getPassword();

        if (!requestPassword.equals(password)) {
            throw new IllegalArgumentException("wrong password");
        }

        post.setTitle(postUpdateRequestDto.getTitle());
        post.setContent(postUpdateRequestDto.getContent());

        final Post savedPost = postRepository.save(post);

        return PostInfoDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .account(savedPost.getAccount())
                .build();
    }
}

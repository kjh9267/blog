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

        return PostInfoDto.from(post);
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
        final Post requestPost = postUpdateRequestDto.toEntity();
        final Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(IllegalArgumentException::new);

        checkPassword(requestPost, post);

        post.setTitle(requestPost.getTitle());
        post.setContent(requestPost.getContent());
        final Post savedPost = postRepository.save(post);

        return PostInfoDto.from(savedPost);
    }

    private void checkPassword(Post requestPost, Post post) {
        final String password = post.getAccount().getPassword();
        final String requestPassword = requestPost.getAccount().getPassword();

        if (!requestPassword.equals(password)) {
            throw new IllegalArgumentException("wrong password");
        }
    }
}

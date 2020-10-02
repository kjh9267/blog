package me.jun.guestbook.service;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final AccountRepository accountRepository;

    public PostResponseDto readPost(PostReadRequestDto postReadRequestDto) {
        Long id = postReadRequestDto.getId();

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostResponseDto.from(post);
    }

    @Transactional
    public void createPost(PostCreateRequestDto postCreateRequestDto) {
        final Post post = postCreateRequestDto.toEntity();
        final String accountEmail = postCreateRequestDto.getAccountEmail();
        final Account account = accountRepository.findByEmail(accountEmail)
                .orElseThrow(IllegalArgumentException::new);

        post.setAccount(account);

        postRepository.save(post);
    }

    public void deletePost(PostDeleteRequestDto postDeleteRequestDto) {
        final Long id = postDeleteRequestDto.getId();

        postRepository.deleteById(id);
    }

    public PostResponseDto updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        final Post requestPost = postUpdateRequestDto.toEntity();
        final Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(IllegalArgumentException::new);

        checkPassword(requestPost, post);

        post.setTitle(requestPost.getTitle());
        post.setContent(requestPost.getContent());
        final Post savedPost = postRepository.save(post);

        return PostResponseDto.from(savedPost);
    }

    public PostsResponseDto readPostByPage(PostsRequestDto postsRequestDto) {
        final int requestPage = postsRequestDto.getPage();
        final int size = 10;

        final Page<Post> posts = postRepository.findAll(PageRequest.of(requestPage, size));

        return PostsResponseDto.from(posts);
    }

    private void checkPassword(Post requestPost, Post post) {
        final String password = post.getAccount().getPassword();
        final String requestPassword = requestPost.getAccount().getPassword();

        if (!requestPassword.equals(password)) {
            throw new IllegalArgumentException("wrong password");
        }
    }
}

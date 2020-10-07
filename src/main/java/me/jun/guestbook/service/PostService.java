package me.jun.guestbook.service;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.*;
import me.jun.guestbook.exception.EmailNotFoundException;
import me.jun.guestbook.exception.PostNotFoundException;
import me.jun.guestbook.exception.WrongPasswordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final AccountRepository accountRepository;

    @Transactional
    public PostResponseDto readPost(PostReadRequestDto postReadRequestDto) {
        Long id = postReadRequestDto.getId();

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        final Account account = post.getAccount();

        return PostResponseDto.of(post, account);
    }

    @Transactional
    public void createPost(PostCreateRequestDto postCreateRequestDto) {
        final Post post = postCreateRequestDto.toEntity();
        final String accountEmail = postCreateRequestDto.getAccountEmail();
        final Account account = accountRepository.findByEmail(accountEmail)
                .orElseThrow(EmailNotFoundException::new);

        post.setAccount(account);

        postRepository.save(post);
    }

    public void deletePost(PostDeleteRequestDto postDeleteRequestDto) {
        final Long id = postDeleteRequestDto.getId();

        postRepository.deleteById(id);
    }

    @Transactional
    public PostResponseDto updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        final Post requestPost = postUpdateRequestDto.toEntity();
        final Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        final String requestEmail = postUpdateRequestDto.getAccountEmail();
        final Account account = accountRepository.findByEmail(requestEmail)
                .orElseThrow(EmailNotFoundException::new);

        final String requestPassword = postUpdateRequestDto.getPassword();
        final String password = post.getAccount().getPassword();

        checkPassword(requestPassword, password);

        post.setTitle(requestPost.getTitle());
        post.setContent(requestPost.getContent());
        final Post savedPost = postRepository.save(post);

        return PostResponseDto.of(savedPost, account);
    }

    public ManyPostResponseDto readPostByPage(ManyPostRequestDto manyPostRequestDto) {
        final int requestPage = manyPostRequestDto.getPage();
        final int size = 10;

        final Page<Post> posts = postRepository.findAll(PageRequest.of(requestPage, size));

        return ManyPostResponseDto.from(posts);
    }

    private void checkPassword(String requestPassword, String password) {
        if (!requestPassword.equals(password)) {
            throw new WrongPasswordException();
        }
    }
}

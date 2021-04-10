package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.*;
import me.jun.guestbook.application.exception.EmailNotFoundException;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.account.AccountRepository;
import me.jun.guestbook.domain.post.Post;
import me.jun.guestbook.domain.post.PostRepository;
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
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        Account account = post.getAccount();

        return PostResponseDto.of(post, account);
    }

    @Transactional
    public void createPost(PostCreateRequestDto postCreateRequestDto) {
        Post post = postCreateRequestDto.toEntity();
        String accountEmail = postCreateRequestDto.getAccountEmail();
        Account account = accountRepository.findByEmail(accountEmail)
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
        Post requestPost = postUpdateRequestDto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        post.setTitle(requestPost.getTitle());
        post.setContent(requestPost.getContent());
        Post savedPost = postRepository.save(post);

        Account account = savedPost.getAccount();

        return PostResponseDto.of(savedPost, account);
    }

    public ManyPostResponseDto readPostByPage(ManyPostRequestDto manyPostRequestDto) {
        int requestPage = manyPostRequestDto.getPage();
        int size = 10;

        Page<Post> posts = postRepository.findAll(PageRequest.of(requestPage, size));

        return ManyPostResponseDto.from(posts);
    }
}

package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.exception.AccountNotFoundException;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.account.AccountRepository;
import me.jun.guestbook.domain.post.Post;
import me.jun.guestbook.domain.post.PostRepository;
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

    @Transactional
    public PostResponse readPost(PostIdRequest dto) {
        Long id = dto.getId();

        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        Account account = post.getAccount();

        return PostResponse.of(post, account);
    }

    @Transactional
    public void createPost(PostRequest postRequest, Long accountId) {
        Post post = postRequest.toEntity();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);
        post.setAccount(account);

        accountRepository.save(account);
    }

    public void deletePost(PostRequest dto) {
        Long id = dto.getId();

        postRepository.deleteById(id);
    }

    @Transactional
    public PostResponse updatePost(PostRequest dto) {
        Post requestPost = dto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        post.setTitle(requestPost.getTitle());
        post.setContent(requestPost.getContent());
        Post newPost = postRepository.save(post);

        Account account = newPost.getAccount();

        return PostResponse.of(newPost, account);
    }

    public ManyPostResponseDto readPostByPage(ManyPostRequestDto manyPostRequestDto) {
        int requestPage = manyPostRequestDto.getPage();
        int size = 10;

        Page<Post> posts = postRepository.findAll(PageRequest.of(requestPage, size));

        return ManyPostResponseDto.from(posts);
    }
}

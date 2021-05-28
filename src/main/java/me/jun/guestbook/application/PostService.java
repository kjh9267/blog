package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.exception.GuestNotFoundException;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
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

    private final GuestRepository guestRepository;

    @Transactional
    public PostResponse readPost(PostIdRequest dto) {
        Long id = dto.getId();

        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        Guest guest = post.getGuest();

        return PostResponse.of(post, guest);
    }

    @Transactional
    public void createPost(PostRequest postRequest, Long accountId) {
        Post post = postRequest.toEntity();

        Guest guest = guestRepository.findById(accountId)
                .orElseThrow(GuestNotFoundException::new);
        post.setGuest(guest);

        guestRepository.save(guest);
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

        Guest guest = newPost.getGuest();

        return PostResponse.of(newPost, guest);
    }

    public ManyPostResponseDto readPostByPage(ManyPostRequestDto manyPostRequestDto) {
        int requestPage = manyPostRequestDto.getPage();
        int size = 10;

        Page<Post> posts = postRepository.findAll(PageRequest.of(requestPage, size));

        return ManyPostResponseDto.from(posts);
    }
}

package me.jun.guestbook.post.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.exception.GuestNotFoundException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.post.application.exception.GuestMisMatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.post.domain.Post;
import me.jun.guestbook.post.domain.PostRepository;
import me.jun.guestbook.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final GuestRepository guestRepository;

    public void createPost(PostCreateRequest postCreateRequest, Long guestId) {
        Post post = postCreateRequest.toEntity();
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(GuestNotFoundException::new);
//        post.setGuest(guest);
        post.setGuestId(guestId);

        guestRepository.save(guest);
    }

    public PostResponse readPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

//        Guest guest = guestRepository.findById(guestId)
//                .orElseThrow(GuestNotFoundException::new);

        return PostResponse.of(post, null);
    }

    public void updatePost(PostUpdateRequest dto, Long guestId) {
        Post requestPost = dto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        Long id = post.getGuestId();
        if (!id.equals(guestId)) {
            throw new GuestMisMatchException("guest mismatch");
        }

        post.updatePost(requestPost.getTitle(), requestPost.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long postId, Long guestId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Long id = post.getGuestId();
        if (!id.equals(guestId)) {
            throw new GuestMisMatchException("guest mismatch");
        }

        postRepository.deleteById(postId);
    }

    public ManyPostResponseDto readPostByPage(ManyPostRequestDto manyPostRequestDto) {
        int requestPage = manyPostRequestDto.getPage();
        int size = 10;

        Page<Post> posts = postRepository.findAll(PageRequest.of(requestPage, size));

        return ManyPostResponseDto.from(posts);
    }
}

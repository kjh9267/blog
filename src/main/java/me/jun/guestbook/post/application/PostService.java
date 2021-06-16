package me.jun.guestbook.post.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.*;
import me.jun.guestbook.post.application.exception.WriterMisMatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.post.domain.Post;
import me.jun.guestbook.post.domain.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostCreateRequest postCreateRequest, Long writerId) {
        Post post = postCreateRequest.toEntity();
        post.setWriterId(writerId);

        postRepository.save(post);
    }

    public PostResponse readPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.of(post);
    }

    public void updatePost(PostUpdateRequest dto, Long writerId) {
        Post requestPost = dto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        Long id = post.getWriterId();
        if (!id.equals(writerId)) {
            throw new WriterMisMatchException("writer mismatch");
        }

        String title = requestPost.getTitle();
        String content = requestPost.getContent();
        post.updatePost(title, content);
        postRepository.save(post);
    }

    public void deletePost(Long postId, Long writerId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Long id = post.getWriterId();
        if (!id.equals(writerId)) {
            throw new WriterMisMatchException("writer mismatch");
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

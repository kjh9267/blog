package me.jun.core.guestbook.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.core.guestbook.application.dto.*;
import me.jun.core.guestbook.application.exception.PostNotFoundException;
import me.jun.core.guestbook.domain.Post;
import me.jun.core.guestbook.domain.PostWriter;
import me.jun.core.guestbook.domain.repository.PostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final CommentService commentService;

    private final PostCountService postCountService;

    public PostResponse createPost(PostCreateRequest postCreateRequest) {
        Post post = postCreateRequest.toEntity();
        post = postRepository.save(post);

        Long postId = post.getId();
        postCountService.createPostCount(postId);

        return PostResponse.of(post);
    }

    @Retryable(
            maxAttempts = Integer.MAX_VALUE,
            value = OptimisticLockingFailureException.class
    )
    public PostResponse retrievePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        postCountService.updateHits(postId);

        return PostResponse.of(post);
    }

    @CachePut(cacheNames = "postStore")
    public PostResponse updatePost(PostUpdateRequest request) {
        Post requestPost = request.toEntity();
        Post updatedPost = postRepository.findById(requestPost.getId())
                .map(post -> post.validateWriter(request.getWriterId()))
                .map(post -> post.updatePost(
                        requestPost.getTitle(),
                        requestPost.getContent()
                ))
                .orElseThrow(() -> new PostNotFoundException(requestPost.getId()));

        return PostResponse.of(updatedPost);
    }

    @CacheEvict(cacheNames = "postStore", key = "#request.getPostId()")
    public Long deletePost(PostDeleteRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new PostNotFoundException(request.getPostId()));

        post.validateWriter(request.getWriterId());

        commentService.deleteCommentByPostId(request.getPostId());
        postRepository.deleteById(request.getPostId());

        return request.getPostId();
    }

    public void deletePostByWriterId(Long writerId) {
        PostWriter postWriter = new PostWriter(writerId);
        postRepository.deleteAllByPostWriter(postWriter);
    }

    public PagedPostsResponse queryPosts(PageRequest request) {
        Page<Post> posts = postRepository.findAll(request);
        return PagedPostsResponse.from(posts);
    }
}

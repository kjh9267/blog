package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.dto.PostCreateRequest;
import me.jun.guestbook.application.dto.PostResponse;
import me.jun.guestbook.application.dto.PostUpdateRequest;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.domain.PostWriter;
import me.jun.guestbook.domain.repository.PostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final CommentService commentService;

    private final PostCountService postCountService;

    @Async
    public CompletableFuture<PostResponse> createPost(PostCreateRequest postCreateRequest, Long writerId) {
        Post post = postCreateRequest.toEntity();

        post.setPostWriter(new PostWriter(writerId));
        post = postRepository.save(post);

        Long postId = post.getId();
        postCountService.createPostCount(postId);

        return CompletableFuture.completedFuture(
                PostResponse.of(post)
        );
    }


    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<PostResponse> retrievePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postCountService.updateHits(postId);

        return CompletableFuture.completedFuture(
                PostResponse.of(post)
        );
    }

    @Async
    @CachePut(cacheNames = "posts")
    public CompletableFuture<PostResponse> updatePost(PostUpdateRequest dto, Long writerId) {
        Post requestPost = dto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        post.validateWriter(writerId);

        post = post.updatePost(
                requestPost.getTitle(),
                requestPost.getContent()
        );

        return CompletableFuture.completedFuture(
                PostResponse.of(post)
        );
    }

    @Async
    @CacheEvict(cacheNames = "posts", key = "#postId")
    public CompletableFuture<Long> deletePost(Long postId, Long writerId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        post.validateWriter(writerId);

        postRepository.deleteById(postId);
        commentService.deleteCommentByPostId(postId);

        return CompletableFuture.completedFuture(postId);
    }

    @Async
    public CompletableFuture<Void> deletePostByWriterId(Long writerId) {
        PostWriter postWriter = new PostWriter(writerId);
        postRepository.deleteAllByPostWriter(postWriter);

        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<Page<Post>> queryPosts(PageRequest request) {
        return CompletableFuture.completedFuture(
                postRepository.findAll(request)
        );
    }
}

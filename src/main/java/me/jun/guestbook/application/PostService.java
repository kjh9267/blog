package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public PostResponse createPost(PostCreateRequest postCreateRequest, String writerEmail) {
        Post post = postCreateRequest.toEntity();

        post.setPostWriter(new PostWriter(writerEmail));
        post = postRepository.save(post);

        Long postId = post.getId();
        postCountService.createPostCount(postId);

        return PostResponse.of(post);
    }

    @Transactional(readOnly = true)
    public PostResponse retrievePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        postCountService.updateHits(postId);

        return PostResponse.of(post);
    }

    @CachePut(cacheNames = "postStore")
    public PostResponse updatePost(PostUpdateRequest dto, String writerEmail) {
        Post requestPost = dto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(() -> new PostNotFoundException(requestPost.getId()));

        post.validateWriter(writerEmail);

        post = post.updatePost(
                requestPost.getTitle(),
                requestPost.getContent()
        );

        return PostResponse.of(post);
    }

    @CacheEvict(cacheNames = "postStore", key = "#postId")
    public Long deletePost(Long postId, String writerEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        post.validateWriter(writerEmail);

        postRepository.deleteById(postId);
        commentService.deleteCommentByPostId(postId);

        return postId;
    }

    public void deletePostByWriterEmail(String writerEmail) {
        PostWriter postWriter = new PostWriter(writerEmail);
        postRepository.deleteAllByPostWriter(postWriter);
    }

    public PagedPostsResponse queryPosts(PageRequest request) {
        Page<Post> posts = postRepository.findAll(request);
        return PagedPostsResponse.from(posts);
    }
}

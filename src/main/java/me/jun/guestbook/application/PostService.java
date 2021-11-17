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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final CommentService commentService;

    private final PostCountService postCountService;

    public PostResponse createPost(PostCreateRequest postCreateRequest, Long writerId) {
        Post post = postCreateRequest.toEntity();

        post.setPostWriter(new PostWriter(writerId));
        post = postRepository.save(post);

        Long postId = post.getId();
        postCountService.createPostCount(postId);

        return PostResponse.of(post);
    }

    public PostResponse retrievePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postCountService.updateHits(postId);

        return PostResponse.of(post);
    }

    public PostResponse updatePost(PostUpdateRequest dto, Long writerId) {
        Post requestPost = dto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        post.validateWriter(writerId);

        String title = requestPost.getTitle();
        String content = requestPost.getContent();
        post.updatePost(title, content);

        return PostResponse.of(post);
    }

    public Long deletePost(Long postId, Long writerId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        post.validateWriter(writerId);

        postRepository.deleteById(postId);
        commentService.deleteCommentByPostId(postId);
        return postId;
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

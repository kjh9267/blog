package me.jun.guestbook.post.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.post.application.dto.PagedPostsResponse;
import me.jun.guestbook.post.application.dto.PostCreateRequest;
import me.jun.guestbook.post.application.dto.PostResponse;
import me.jun.guestbook.post.application.dto.PostUpdateRequest;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.post.domain.Post;
import me.jun.guestbook.post.domain.PostRepository;
import me.jun.guestbook.post.domain.PostWriter;
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

    public PostResponse createPost(PostCreateRequest postCreateRequest, Long writerId) {
        Post post = postCreateRequest.toEntity();
        post.setPostWriter(new PostWriter(writerId));
        Post savedPost = postRepository.save(post);
        return PostResponse.of(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse readPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

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

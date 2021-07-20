package me.jun.guestbook.post.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.post.application.exception.WriterMismatchException;
import me.jun.guestbook.post.domain.Post;
import me.jun.guestbook.post.domain.PostRepository;
import me.jun.guestbook.post.presentation.dto.PagedPostsResponse;
import me.jun.guestbook.post.presentation.dto.PostCreateRequest;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.post.presentation.dto.PostUpdateRequest;
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
        post.setWriterId(writerId);
        Post savedPost = postRepository.save(post);
        return PostResponse.of(savedPost);
    }

    public PostResponse readPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.of(post);
    }

    public PostResponse updatePost(PostUpdateRequest dto, Long writerId) {
        Post requestPost = dto.toEntity();
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(PostNotFoundException::new);

        Long id = post.getWriterId();
        if (!id.equals(writerId)) {
            throw new WriterMismatchException("writer mismatch");
        }

        String title = requestPost.getTitle();
        String content = requestPost.getContent();
        post.updatePost(title, content);
        Post savedPost = postRepository.save(post);

        return PostResponse.of(savedPost);
    }

    public Long deletePost(Long postId, Long writerId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Long id = post.getWriterId();
        if (!id.equals(writerId)) {
            throw new WriterMismatchException("writer mismatch");
        }

        postRepository.deleteById(postId);
        commentService.deleteCommentByPostId(postId);
        return postId;
    }

    public void deletePostByWriterId(Long writerId) {
        postRepository.deleteByWriterId(writerId);
    }

    public PagedPostsResponse queryPosts(PageRequest request) {
        Page<Post> posts = postRepository.findAll(request);
        return PagedPostsResponse.from(posts);
    }
}

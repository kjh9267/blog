package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.post.presentation.dto.PostCreateRequest;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.post.presentation.dto.PostUpdateRequest;
import me.jun.guestbook.post.presentation.dto.WriterInfo;
import me.jun.guestbook.security.Writer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<EntityModel<PostResponse>> createPost(@RequestBody PostCreateRequest request,
                                           @Writer WriterInfo writer) {
        PostResponse postResponse = postService.createPost(request, writer.getId());

        WebMvcLinkBuilder selfLinkBuilder = linkTo(PostController.class)
                .slash(postResponse.getId());
        URI selfUri = selfLinkBuilder
                .withSelfRel()
                .toUri();

        return ResponseEntity.created(selfUri)
                .body(
                        EntityModel.of(postResponse)
                        .add(selfLinkBuilder.withSelfRel())
                        .add(selfLinkBuilder.withRel("get_post"))
                        .add(selfLinkBuilder.withRel("update_post"))
                        .add(selfLinkBuilder.withRel("delete_post"))
                );
    }

    @GetMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<EntityModel<PostResponse>> readPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.readPost(postId);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(PostController.class)
                .slash(postResponse.getId());

        return ResponseEntity.ok(
                EntityModel.of(postResponse)
                        .add(selfLinkBuilder.withSelfRel())
                        .add(linkTo(PostController.class).withRel("create_post"))
                        .add(selfLinkBuilder.withRel("update_post"))
                        .add(selfLinkBuilder.withRel("delete_post"))
        );
    }

    @PutMapping
    public ResponseEntity<EntityModel<PostResponse>> updatePost(@RequestBody PostUpdateRequest requestDto,
                                           @Writer WriterInfo writer) {
        PostResponse postResponse = postService.updatePost(requestDto, writer.getId());

        WebMvcLinkBuilder selfLinkBuilder = linkTo(PostController.class)
                .slash(postResponse.getId());

        return ResponseEntity.ok(
                EntityModel.of(postResponse)
                        .add(selfLinkBuilder.withSelfRel())
                        .add(selfLinkBuilder.withRel("get_post"))
                        .add(linkTo(PostController.class).withRel("create_post"))
                        .add(selfLinkBuilder.withRel("delete_post"))
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<RepresentationModel> deletePost(@PathVariable Long postId,
                                           @Writer WriterInfo writer) {
        postService.deletePost(postId, writer.getId());

        WebMvcLinkBuilder selfLinkBuilder = linkTo(PostController.class);

        return ResponseEntity.ok(
                new RepresentationModel<>()
                        .add(selfLinkBuilder.withSelfRel())
                        .add(linkTo(PostController.class).withRel("create_post")));
    }
}

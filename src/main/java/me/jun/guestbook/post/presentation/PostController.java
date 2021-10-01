package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.post.application.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/posts")
public class PostController {

    private final PostService postService;

    private final PostEntityModelCreator entityModelCreator;

    @PostMapping
    public ResponseEntity<EntityModel<PostResponse>>
    createPost(@RequestBody @Valid PostCreateRequest request,
               @PostWriter PostWriterInfo writer) {
        PostResponse postResponse = postService.createPost(request, writer.getId());
        URI selfUri = createSelfUri(postResponse);

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createEntityModel(postResponse));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<EntityModel<PostResponse>> readPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.readPost(postId);

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(postResponse));
    }

    @PutMapping
    public ResponseEntity<EntityModel<PostResponse>>
    updatePost(@RequestBody @Valid PostUpdateRequest requestDto,
               @PostWriter PostWriterInfo writer) {
        PostResponse postResponse = postService.updatePost(requestDto, writer.getId());

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(postResponse));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<RepresentationModel>
    deletePost(@PathVariable Long postId,
               @PostWriter PostWriterInfo writer) {
        postService.deletePost(postId, writer.getId());

        return ResponseEntity.ok()
                .body(entityModelCreator.createHyperlinks());
    }

    @GetMapping("/query")
    ResponseEntity<CollectionModel<EntityModel<PostResponse>>>
    queryPosts(@PathParam("page") Integer page,
               @PathParam("size") Integer size) {
        PagedPostsResponse response = postService.queryPosts(PageRequest.of(page, size));
        return ResponseEntity.ok()
                .body(entityModelCreator.createCollectionModel(response));
    }

    private URI createSelfUri(PostResponse postResponse) {
        return linkTo(getClass())
                .slash(postResponse.getId())
                .withSelfRel()
                .toUri();
    }
}

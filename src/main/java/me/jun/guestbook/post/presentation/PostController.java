package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.common.EntityModelCreator;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.post.presentation.dto.PostCreateRequest;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.post.presentation.dto.PostUpdateRequest;
import me.jun.guestbook.post.presentation.dto.PostWriterInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/posts")
public class PostController {

    private final PostService postService;

    @Qualifier("postEntityModelCreator")
    private final EntityModelCreator<PostResponse> entityModelCreator;

    @PostMapping
    public ResponseEntity<EntityModel<PostResponse>> createPost(@RequestBody PostCreateRequest request,
                                           @PostWriter PostWriterInfo writer) {

        PostResponse postResponse = postService.createPost(request, writer.getId());
        URI selfUri = createSelfUri(postResponse);

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createEntityModel(postResponse, getClass()));
    }

    @GetMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<EntityModel<PostResponse>> readPost(@PathVariable Long postId) {

        PostResponse postResponse = postService.readPost(postId);

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(postResponse, getClass()));
    }

    @PutMapping
    public ResponseEntity<EntityModel<PostResponse>> updatePost(@RequestBody PostUpdateRequest requestDto,
                                           @PostWriter PostWriterInfo writer) {

        PostResponse postResponse = postService.updatePost(requestDto, writer.getId());

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(postResponse, getClass()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<RepresentationModel> deletePost(@PathVariable Long postId,
                                           @PostWriter PostWriterInfo writer) {

        postService.deletePost(postId, writer.getId());

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(getClass()));
    }

    private URI createSelfUri(PostResponse postResponse) {
        return linkTo(getClass())
                .slash(postResponse.getId())
                .withSelfRel()
                .toUri();
    }
}

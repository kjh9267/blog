package me.jun.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.guestbook.application.PostCountService;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.application.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static reactor.core.scheduler.Schedulers.elastic;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/posts")
public class PostController {

    private final PostService postService;

    private final PostCountService postCountService;

    @PostMapping
    public ResponseEntity<Mono<PostResponse>> createPost(@RequestBody @Valid PostCreateRequest request,
                                                        @PostWriter PostWriterInfo writer) {

        Mono<PostResponse> postResponseMono = Mono.fromCompletionStage(
                () -> postService.createPost(request, writer.getEmail())
        ).log();

        return ResponseEntity.ok()
                .body(postResponseMono);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Mono<PostResponse>> retrievePost(@PathVariable Long postId) {

        Mono<PostResponse> postResponseMono = Mono.fromCompletionStage(
                () -> postService.retrievePost(postId)
                )
                .log()
                .publishOn(elastic())
                .map(postResponse -> {
                    postCountService.updateHits(postId);
                    return postResponse;
                })
                .log();

        log.info("before subscribe");

        return ResponseEntity.ok()
                .body(postResponseMono);
    }

    @PutMapping
    public ResponseEntity<Mono<PostResponse>> updatePost(@RequestBody @Valid PostUpdateRequest requestDto,
                                                         @PostWriter PostWriterInfo writer) {

        Mono<PostResponse> postResponseMono = Mono.fromCompletionStage(
                () -> postService.updatePost(requestDto, writer.getEmail())
        ).log();

        return ResponseEntity.ok()
                .body(postResponseMono);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Mono<Long>> deletePost(@PathVariable Long postId,
                                                 @PostWriter PostWriterInfo writer) {
        Mono<Long> mono = Mono.fromCompletionStage(
                () -> postService.deletePost(postId, writer.getEmail())
        ).log();

        return ResponseEntity.ok()
                .body(mono);
    }

    @GetMapping("/query")
    ResponseEntity<Flux<PagedPostsResponse>> queryPosts(@RequestParam("page") Integer page,
                                                        @RequestParam("size") Integer size) {

//        PagedPostsResponse response = postService.queryPosts(PageRequest.of(page, size));

        return null;


//        return ResponseEntity.ok()
//                .body(postFlux);
    }
}

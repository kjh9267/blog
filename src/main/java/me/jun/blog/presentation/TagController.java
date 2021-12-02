package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.TaggedArticleService;
import me.jun.blog.application.dto.AddTagRequest;
import me.jun.blog.application.dto.TaggedArticleResponse;
import me.jun.member.application.dto.MemberInfo;
import me.jun.support.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class TagController {

    private final TaggedArticleService taggedArticleService;

    @PostMapping("/tag")
    public ResponseEntity<TaggedArticleResponse> AddTag(@RequestBody @Valid AddTagRequest request,
                                                        @Member MemberInfo memberInfo) {

        TaggedArticleResponse response = taggedArticleService.addTagToArticle(request);

        return ResponseEntity.ok()
                .body(response);
    }
}

package me.jun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.PostCreateRequestDto;
import me.jun.guestbook.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public String createPost(@RequestBody PostCreateRequestDto request) {
        postService.createPost(request);

        return "redirect:/index";
    }
}

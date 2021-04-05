package me.jun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public String createPost(@RequestBody PostCreateRequestDto request) {
        postService.createPost(request);

        return "redirect:/index";
    }

    @GetMapping("/post/{id}")
    public String readPost(@ModelAttribute PostReadRequestDto id,
                           Model model) {
        final PostResponseDto postResponseDto = postService.readPost(id);
        model.addAttribute("post", postResponseDto);

        return "/post";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@ModelAttribute PostDeleteRequestDto id) {
        postService.deletePost(id);

        return "redirect:/index";
    }

    @PutMapping("/post/{id}")
    public String updatePost(@PathVariable Long id,
                             @RequestBody PostUpdateRequestDto requestDto,
                             Model model) {
        requestDto.setId(id);

        final PostResponseDto postResponseDto = postService.updatePost(requestDto);

        model.addAttribute("post", postResponseDto);

        return "/post";
    }
}

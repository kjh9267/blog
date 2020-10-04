package me.jun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.PostCreateRequestDto;
import me.jun.guestbook.dto.PostDeleteRequestDto;
import me.jun.guestbook.dto.PostReadRequestDto;
import me.jun.guestbook.dto.PostResponseDto;
import me.jun.guestbook.service.PostService;
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
        System.out.println(id);
        System.out.println(id.getId());
        postService.deletePost(id);

        return "redirect:/index";
    }
}

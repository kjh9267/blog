package me.jun.guestbook.ui;

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
    public String createPost(@RequestBody PostRequestDto request) {
        postService.createPost(request);

        return "redirect:/index";
    }

    @GetMapping("/post/{id}")
    public String readPost(@ModelAttribute PostRequestDto id,
                           Model model) {
        final PostResponseDto postResponseDto = postService.readPost(id);
        model.addAttribute("post", postResponseDto);

        return "/post";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@ModelAttribute PostRequestDto id) {
        postService.deletePost(id);

        return "redirect:/index";
    }

    @PutMapping("/post")
    public String updatePost(@RequestBody PostRequestDto requestDto,
                             Model model) {

        final PostResponseDto postResponseDto = postService.updatePost(requestDto);

        model.addAttribute("post", postResponseDto);

        return "/post";
    }
}

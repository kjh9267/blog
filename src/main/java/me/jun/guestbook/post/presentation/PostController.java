package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public String createPost(@RequestBody PostRequest request) {
        postService.createPost(request, 1L);

        return "redirect:/index";
    }

    @GetMapping("/post/{id}")
    public String readPost(@ModelAttribute PostIdRequest id,
                           Model model) {
        PostResponse postResponse = postService.readPost(id);
        model.addAttribute("post", postResponse);

        return "/post";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@ModelAttribute PostRequest id) {
        postService.deletePost(id);

        return "redirect:/index";
    }

    @PutMapping("/post")
    public String updatePost(@RequestBody PostRequest requestDto,
                             Model model) {

        final PostResponse postResponse = postService.updatePost(requestDto);

        model.addAttribute("post", postResponse);

        return "/post";
    }
}

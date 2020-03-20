package me.jun.guestbook.controller;

import me.jun.guestbook.dto.PostSaveDto;
import me.jun.guestbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/home")
    public String getList(Model model) {
        model.addAttribute("list", postService.getList());
        return "home";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute @Valid PostSaveDto postSaveDto) {
        postService.savePost(postSaveDto);
        return "redirect:home";
    }

    @PostMapping("/delete")
    public String delete(Long id, String password) {
        postService.deletePost(id, password);
        return "redirect:home";
    }
}

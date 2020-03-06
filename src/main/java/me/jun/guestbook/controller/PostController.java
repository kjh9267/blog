package me.jun.guestbook.controller;

import me.jun.guestbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/home")
    public String getList(Model model) {
        model.addAttribute("list", postService.getList());
        return "home";
    }
}

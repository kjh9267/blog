package me.jun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.ManyPostRequestDto;
import me.jun.guestbook.dto.ManyPostResponseDto;
import me.jun.guestbook.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostService postService;

    @GetMapping({"/", "/index"})
    public String index() {

        return "redirect:/index/1";
    }

    @GetMapping("/index/{page}")
    public String index(Model model,
            ManyPostRequestDto page) {

        final List<ManyPostResponseDto.PostResponse> content = postService.readPostByPage(page)
                .getPostInfoDtoPage()
                .getContent();

        for(ManyPostResponseDto.PostResponse data:content) {
            System.out.println(data);
        }


        model.addAttribute("list",
                postService.readPostByPage(page)
                        .getPostInfoDtoPage()
                        .getContent()
                );

        System.out.println(model.getAttribute("list"));

        return "/index";
    }
}

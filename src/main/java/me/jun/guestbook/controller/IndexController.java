package me.jun.guestbook.controller;

import me.jun.guestbook.dto.PostInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/index")
    @ResponseBody
    public ModelAndView index() {
        final ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/index");

        return modelAndView;
    }
}

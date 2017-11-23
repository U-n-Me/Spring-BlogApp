package com.ume.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {

    @RequestMapping("/allblogs")
    public String greeting() {
        return "startPage";
    }
    
    @RequestMapping("/allblogs/@ume")
    public String bloggerBlogs() {
        return "startPage";
    }
}

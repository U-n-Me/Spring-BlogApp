package com.ume.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {

    @RequestMapping("/allblogs")
    public String allBlogs() {
        return "startPage";
    }
    
    @RequestMapping("/blog")
    public String blogPage() {
        return "blogPage";
    }    

    @RequestMapping("/newBlog")
    public String createBlog() {
        return "createBlog";
    }
}

package com.almod.DemoSpring.controller;



import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    @Autowired
    private PostService postService;

    @GetMapping("/blog")
    public String blog(Model model){
        Iterable<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);
        return "blog";
    }
}

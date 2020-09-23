package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessagesController {
    private PostService postService;

    @Autowired
    public void setPostService(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/blog/{id}/messages")
    public String messages(@PathVariable long id, Model model){
        Iterable<Post> posts = postService.findAll();

        List<Post> postsById = new ArrayList<>();
        for(Post post : posts){
            if(post.getUsr().getId() == id){
                postsById.add(post);
            }
        }

        model.addAttribute("posts", postsById);

        return "messages";
    }
}

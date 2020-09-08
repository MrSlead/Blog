package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.repo.PostRepo;
import com.almod.DemoSpring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blogAdd";
    }

    @PostMapping("/blog/add")
    public String add(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text){
        Post post = new Post(title, anons, full_text);

        postService.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String details(@PathVariable long id, Model model){
        Optional<Post> post = postService.findById(id);
        if(!postService.existsById(id)){
            return "redirect:/blog";
        }

        List<Post> list = new ArrayList<>();
        post.ifPresent(list::add);

        model.addAttribute("posts", list);
        return "blogDetails";
    }

    @GetMapping("/blog/{id}/edit")
    public String edit(@PathVariable long id, Model model){
        Optional<Post> post = postService.findById(id);
        if(!postService.existsById(id)){
            return "redirect:/blog";
        }

        List<Post> list = new ArrayList<>();
        post.ifPresent(list::add);

        model.addAttribute("posts", list);
        return "blogEdit";
    }

    @PostMapping("/blog/{id}/edit")
    public String update(@PathVariable long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Optional<Post> post = postService.findById(id);
        if(!postService.existsById(id)){
            return "redirect:/blog";
        }

        post.get().setTitle(title);
        post.get().setAnons(anons);
        post.get().setFull_text(full_text);
        postService.save(post.get());

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String update(@PathVariable long id, Model model){
        Optional<Post> post = postService.findById(id);
        if(!postService.existsById(id)){
            return "redirect:/blog";
        }
        postService.delete(post.get());

        return "redirect:/blog";
    }
}

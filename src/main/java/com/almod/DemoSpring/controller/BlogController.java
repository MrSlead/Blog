package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.service.PostService;
import com.almod.DemoSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/blog")
    public String blog(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String radiobutton,
                       Model model){

        Iterable<Post> posts = postService.findAll();

        if(!(name == null || name.isEmpty())){
            if(radiobutton != null ) {
                if (radiobutton.equals("rad1")) {
                    posts = postService.findPostsByUsr_Username(name);
                }
                if (radiobutton.equals("rad2")) {
                    posts = postService.findPostsByTitle(name);
                }
            }
        }


        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        Iterable<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);
        return "blogAdd";
    }

    @PostMapping("/blog/add")
    public String add(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text){
        Post post = new Post(title, anons, full_text);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-y");
        post.setDate(sdf.format(date));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setUsr(userService.findByUsername(auth.getName()));


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

        if(SecurityContextHolder.getContext().getAuthentication().getName().equals(post.get().getUsr().getUsername())){
            model.addAttribute("isUserCreator", "edit_and_remove");
        }
        else {
            model.addAttribute("isUserCreator", "nothing");
        }

        return "blogDetails";
    }

    @GetMapping("/blog/{id}/edit")
    public String edit(@PathVariable long id, Model model){
        Optional<Post> post = postService.findById(id);
        if(!postService.existsById(id)){
            return "redirect:/blog";
        }
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(post.get().getUsr().getUsername())){
            return "redirect:/blog/{id}";
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

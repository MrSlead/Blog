package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.enginer.ButtonEnginer;
import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.service.PostService;
import com.almod.DemoSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BlogController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private List<Integer> elements = new ArrayList<>(Arrays.asList(5, 10, 20, 50));

    @GetMapping("/blog")
    public String blog(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String radiobutton,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size,
                       Model model){

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Post> postPage = null;


        if(!(name == null || name.isEmpty()) && (radiobutton != null)) {
            if(radiobutton.equals("rad1") || radiobutton.equals("rad2")) {
                if (!ButtonEnginer.getClickedButton()) {
                    currentPage = 1;
                    ButtonEnginer.doTrue();
                }
            }
        } else ButtonEnginer.doFalse();

        boolean isSearch = false;
        if(!(name == null || name.isEmpty())){
            if(radiobutton != null ) {
                if (radiobutton.equals("rad1")) {
                    isSearch = true;
                    postPage = postService.findPostsByUsr_Username(name, PageRequest.of(currentPage - 1, pageSize));
                }
                if (radiobutton.equals("rad2")) {
                    isSearch = true;
                    postPage = postService.findPostsByTitle(name, PageRequest.of(currentPage - 1, pageSize));
                }
            }
        }

        if(postPage == null){
            postPage = postService.findPaginated((List<Post>) postService.findAll(),
                    PageRequest.of(currentPage - 1, pageSize));
        }

        int totalPages = postPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("value", name);
        model.addAttribute("radiobutton", radiobutton);
        model.addAttribute("isSearch", isSearch);

        model.addAttribute("postPage", postPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("elements", elements);

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

        return "redirect:/blog/{id}";
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

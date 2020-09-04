package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.entity.User;
import com.almod.DemoSpring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/logout-success")
    public String logout(){
        return "logout";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("registration")
    public String addUser(User user, Model model){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        userRepo.save(user);

        return "redirect:/login";
    }
}

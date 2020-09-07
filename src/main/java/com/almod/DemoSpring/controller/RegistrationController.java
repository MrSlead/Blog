package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.entity.User;
import com.almod.DemoSpring.repo.UserRepo;
import com.almod.DemoSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

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
        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("UsernameExistsError", "Username exists!");
            return "registration";
        }
        /*if(user.getUsername().length() < 4 || user.getUsername().length() > 30){
            model.addAttribute("UsernameLengthError",
                    "The name must be longer than 4 characters and less than 30 characters.");
            return "registration";
        }
        if(user.getPassword().length() < 6 || user.getPassword().length() > 80){
            model.addAttribute("PasswordLengthError",
                    "The password must be longer than 6 characters and less than 80 characters.");
            return "registration";
        }*/

        userService.save(user);

        return "redirect:/login";
    }
}

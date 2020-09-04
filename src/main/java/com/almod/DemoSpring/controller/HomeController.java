package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.db.UserPrincipal;
import com.almod.DemoSpring.entity.User;
import com.almod.DemoSpring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public final String root() {
        return "home";
    }
}

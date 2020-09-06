package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.db.UserPrincipal;
import com.almod.DemoSpring.entity.User;
import com.almod.DemoSpring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping(value = {"", "/"})
    public final String root(final Principal principal, Model model) {
        /*if (null == principal) {
            model.addAttribute("authorization", "/login");
            model.addAttribute("textAuth", "Sign in");
        }
        else {
            model.addAttribute("authorization", "/logout");
            model.addAttribute("textAuth", "Logout");
        }*/
        return "home";
    }
}

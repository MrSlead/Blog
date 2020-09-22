package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.dto.CaptchaResponseDto;
import com.almod.DemoSpring.entity.User;
import com.almod.DemoSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class RegistrationController {
    //private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    /*@Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;*/

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            return "redirect:/logout-success";
        }
        if(error != null && error.equals("true")){
            model.addAttribute("PasswordError", "Wrong password or user does not exist!");
        }
        return "login";
    }


    @GetMapping("/logout-success")
    public String logout(){
        return "logoutPage";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("registration")
    public String addUser(User user,
                          @RequestParam String repeat_password,
                          //@RequestParam("g-recaptcha-response") String captcha_response,
                          Model model){

        /*String url = String.format(CAPTCHA_URL, secret, captcha_response);
        CaptchaResponseDto responseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if(!responseDto.isSuccess()){
            model.addAttribute("CaptchaError", "Fill captcha");
            return "registration";
        }*/

        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("UsernameExistsError", "Username exists!");
            return "registration";
        }
        if(!repeat_password.equals(user.getPassword())){
            model.addAttribute("RepeatPasswordError", "The password is not equal to the repeated password!");
            return "registration";
        }
        if(user.getUsername().length() < 4 || user.getUsername().length() > 30){
            model.addAttribute("UsernameLengthError",
                    "The name must be longer than 4 characters and less than 30 characters.");
            return "registration";
        }
        if(user.getPassword().length() < 6 || user.getPassword().length() > 80){
            model.addAttribute("PasswordLengthError",
                    "The password must be longer than 6 characters and less than 80 characters.");
            return "registration";
        }

        userService.save(user);

        return "redirect:/login";
    }
}

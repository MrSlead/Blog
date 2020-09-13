package com.almod.DemoSpring.controller;

import com.almod.DemoSpring.entity.Question;
import com.almod.DemoSpring.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @RequestMapping("/sendSimpleEmail")
    public String sendSimpleEmail() {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("blog.java.webapp@gmail.com");
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        // Send Message!
        this.emailSender.send(message);

        return "Email Sent!";
    }

    @PostMapping("/sendQuestion")
    public String sendQuestion(@RequestParam String name, @RequestParam String email, @RequestParam String textQuestion) throws MessagingException {
        if(!email.endsWith("@gmail.com")){
            return "main";
        }
        Question question = new Question(name, email, textQuestion);
        questionService.save(question);

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        //send a message to the user
        String htmlMsg = "<h3>Hello, <b>" + name +
                "</b>. You asked me a question. Thank you for your feedback. I'll send you a message soon."
                + "<br> <b>Your question: " + textQuestion + "</b></h3>";

        message.setContent(htmlMsg, "text/html");
        helper.setTo(email);
        helper.setSubject("Test send HTML email");

        this.emailSender.send(message);


        //send a question to me
        String htmlMsg2 = "Email: " + email
                        + "<br> Name: " + name
                        + "<br> Question: " + textQuestion;

        message.setContent(htmlMsg2, "text/html");
        helper.setTo("blog.java.webapp@gmail.com");
        helper.setSubject("Question " + question.getId());

        this.emailSender.send(message);

        return "redirect:/home";
    }

}
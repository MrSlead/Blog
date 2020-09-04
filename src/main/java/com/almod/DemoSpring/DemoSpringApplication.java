package com.almod.DemoSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@SpringBootApplication
public class DemoSpringApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoSpringApplication.class, args);
	}



}

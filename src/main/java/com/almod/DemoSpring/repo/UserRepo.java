package com.almod.DemoSpring.repo;

import com.almod.DemoSpring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
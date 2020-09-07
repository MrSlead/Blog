package com.almod.DemoSpring.repo;

import com.almod.DemoSpring.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
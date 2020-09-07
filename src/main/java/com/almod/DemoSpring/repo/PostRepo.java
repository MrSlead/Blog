package com.almod.DemoSpring.repo;

import com.almod.DemoSpring.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {
}

package com.almod.DemoSpring.repo;

import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {
    Iterable<Post> findPostsByUsr_Username(String username);
    Iterable<Post> findPostsByTitle(String title);
}

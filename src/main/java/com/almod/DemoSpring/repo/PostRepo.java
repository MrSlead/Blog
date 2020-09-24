package com.almod.DemoSpring.repo;

import com.almod.DemoSpring.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepo extends CrudRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {
    Page<Post> findPostsByUsr_Username(String username, Pageable pageable);
    Page<Post> findPostsByTitle(String title, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
}

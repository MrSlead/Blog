package com.almod.DemoSpring.service;

import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService implements PostRepo {
    @Autowired
    @Qualifier("postRepo")
    private PostRepo postRepo;

    @Override
    public <S extends Post> S save(S s) {
        return postRepo.save(s);
    }

    @Override
    public <S extends Post> Iterable<S> saveAll(Iterable<S> iterable) {
        return postRepo.saveAll(iterable);
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return postRepo.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return postRepo.existsById(aLong);
    }

    @Override
    public Iterable<Post> findAll() {
        return postRepo.findAll();
    }

    @Override
    public Iterable<Post> findAllById(Iterable<Long> iterable) {
        return postRepo.findAllById(iterable);
    }

    @Override
    public long count() {
        return postRepo.count();
    }

    @Override
    public void deleteById(Long aLong) {
        postRepo.deleteById(aLong);
    }

    @Override
    public void delete(Post post) {
        postRepo.delete(post);
    }

    @Override
    public void deleteAll(Iterable<? extends Post> iterable) {
        postRepo.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        postRepo.deleteAll();
    }

    @Override
    public Iterable<Post> findPostsByUsr_Username(String username) {
        return postRepo.findPostsByUsr_Username(username);
    }

    @Override
    public Iterable<Post> findPostsByTitle(String title) {
        return postRepo.findPostsByTitle(title);
    }
}

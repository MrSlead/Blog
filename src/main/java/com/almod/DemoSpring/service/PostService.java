package com.almod.DemoSpring.service;

import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public List<Post> findPostsByUsr_Username(String username) {
        Iterable<Post> posts = postRepo.findAll();

        List<Post> list = new ArrayList<>();
        for(Post post : posts){
            if(post.getUsr().getUsername().contains(username)){
                list.add(post);
            }
        }
        return list;
    }

    @Override
    public List<Post> findPostsByTitle(String title) {
        Iterable<Post> posts = postRepo.findAll();

        List<Post> list = new ArrayList<>();
        for(Post post : posts){
            if(post.getTitle().contains(title)){
                list.add(post);
            }
        }
        return list;
    }
}

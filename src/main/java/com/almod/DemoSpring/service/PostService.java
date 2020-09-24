package com.almod.DemoSpring.service;

import com.almod.DemoSpring.entity.Post;
import com.almod.DemoSpring.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    public Iterable<Post> findAll(Sort sort) {
        return postRepo.findAll(sort);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepo.findAll(pageable);
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
    public Page<Post> findPostsByUsr_Username(String username, Pageable pageable) {
        Page<Post> posts = findPaginated(pageable);

        List<Post> list = new ArrayList<>();
        for(Post post : posts){
            if(post.getUsr().getUsername().contains(username)){
                list.add(post);
            }
        }
        return new PageImpl<>(list);
    }

    @Override
    public Page<Post> findPostsByTitle(String title, Pageable pageable) {
        Iterable<Post> posts = findPaginated(pageable);

        List<Post> list = new ArrayList<>();
        for(Post post : posts){
            if(post.getTitle().contains(title)){
                list.add(post);
            }
        }
        return new PageImpl<>(list);
    }

    public Page<Post> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Post> list;
        List<Post> posts = (List<Post>) postRepo.findAll();

        if (posts.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, posts.size());
            list = posts.subList(startItem, toIndex);
        }

        Page<Post> postPage
                = new PageImpl<Post>(list, PageRequest.of(currentPage, pageSize), posts.size());

        return postPage;
    }
}

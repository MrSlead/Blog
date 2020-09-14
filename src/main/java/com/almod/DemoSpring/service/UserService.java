package com.almod.DemoSpring.service;

import com.almod.DemoSpring.entity.User;
import com.almod.DemoSpring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserRepo {
    @Autowired
    @Qualifier("userRepo")
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public <S extends User> S save(S s) {
        s.setPassword(passwordEncoder.encode(s.getPassword()));
        return userRepo.save(s);
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return userRepo.saveAll(iterable);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return userRepo.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return userRepo.existsById(aLong);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> iterable) {
        return userRepo.findAllById(iterable);
    }

    @Override
    public long count() {
        return userRepo.count();
    }

    @Override
    public void deleteById(Long aLong) {
        userRepo.deleteById(aLong);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
        userRepo.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        userRepo.deleteAll();
    }
}

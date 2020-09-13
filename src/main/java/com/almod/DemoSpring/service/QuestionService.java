package com.almod.DemoSpring.service;

import com.almod.DemoSpring.entity.Question;
import com.almod.DemoSpring.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService implements QuestionRepo {
    @Autowired
    @Qualifier("questionRepo")
    private QuestionRepo questionRepo;

    @Override
    public <S extends Question> S save(S s) {
        return questionRepo.save(s);
    }

    @Override
    public <S extends Question> Iterable<S> saveAll(Iterable<S> iterable) {
        return questionRepo.saveAll(iterable);
    }

    @Override
    public Optional<Question> findById(Long aLong) {
        return questionRepo.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return questionRepo.existsById(aLong);
    }

    @Override
    public Iterable<Question> findAll() {
        return questionRepo.findAll();
    }

    @Override
    public Iterable<Question> findAllById(Iterable<Long> iterable) {
        return questionRepo.findAllById(iterable);
    }

    @Override
    public long count() {
        return questionRepo.count();
    }

    @Override
    public void deleteById(Long aLong) {
        questionRepo.deleteById(aLong);
    }

    @Override
    public void delete(Question question) {
        questionRepo.delete(question);
    }

    @Override
    public void deleteAll(Iterable<? extends Question> iterable) {
        questionRepo.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        questionRepo.deleteAll();
    }
}

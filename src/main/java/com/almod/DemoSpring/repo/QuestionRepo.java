package com.almod.DemoSpring.repo;

import com.almod.DemoSpring.entity.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends CrudRepository<Question, Long> {
}

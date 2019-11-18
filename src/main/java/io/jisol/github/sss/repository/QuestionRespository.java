package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.Question;
import io.jisol.github.sss.entity.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRespository extends JpaRepository<Question, Integer> {
}

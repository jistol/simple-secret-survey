package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRespository extends JpaRepository<Question, Integer> {
}

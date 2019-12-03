package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRespository extends JpaRepository<Answer, Integer> {
}

package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.Answer;
import io.jisol.github.sss.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRespository extends JpaRepository<Answer, Integer> {
}

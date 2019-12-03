package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.Answer;
import io.jisol.github.sss.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRespository extends JpaRepository<Answer, Integer> {
    Optional<List<Answer>> findBySurvey(Survey survey);
}

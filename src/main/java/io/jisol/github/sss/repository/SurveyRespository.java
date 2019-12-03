package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRespository extends JpaRepository<Survey, Integer> {
}

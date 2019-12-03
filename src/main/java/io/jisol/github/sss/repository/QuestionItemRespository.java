package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.QuestionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionItemRespository extends JpaRepository<QuestionItem, Integer> {
}

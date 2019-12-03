package io.jisol.github.sss.repository;

import io.jisol.github.sss.entity.AnswerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerItemRespository extends JpaRepository<AnswerItem, Integer> {
}

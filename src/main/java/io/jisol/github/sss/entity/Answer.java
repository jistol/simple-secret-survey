package io.jisol.github.sss.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue
    private int answerId;
    
    @Column
    private int score;
    
    @Column
    private int questionCount;
    
    @CreatedDate
    private LocalDateTime createdDate;
}

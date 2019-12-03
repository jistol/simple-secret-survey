package io.jisol.github.sss.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class AnswerItem extends TimeStampEntity {
    @Id
    @GeneratedValue
    private int answerItemId;
    
    @Column
    private int score;
    
    @Column
    private int questionItemId;
}

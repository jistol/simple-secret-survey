package io.jisol.github.sss.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue
    private int questionId;
   
    @Column
    private String description;
}

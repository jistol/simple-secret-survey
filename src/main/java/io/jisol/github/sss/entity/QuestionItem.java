package io.jisol.github.sss.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class QuestionItem extends TimeStampEntity {
    @Id
    @GeneratedValue
    private int questionItemId;
   
    @NotNull
    @Column
    private String description;
    
    public static QuestionItem create(String description) {
        QuestionItem questionItem = new QuestionItem();
        questionItem.setDescription(description);
        return questionItem;
    }
}

package io.jisol.github.sss.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Question extends TimeStampEntity {
    @Id
    @GeneratedValue
    private int questionId;
    
    @Column
    private String name;
    
    @Fetch(FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionId")
    private List<QuestionItem> questionItemList;
}

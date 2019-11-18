package io.jisol.github.sss.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class QuestionGroup {
    @Id
    @GeneratedValue
    private int questionGroupId;
    
    @Column
    private String name;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionId")
    private List<Question> questionList;
}

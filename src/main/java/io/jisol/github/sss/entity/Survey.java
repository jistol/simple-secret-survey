package io.jisol.github.sss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
public class Survey extends TimeStampEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private int surveyId;
    
    @Column
    private String name;
    
    @Column
    private String description;
    
    @OneToOne
    @JoinColumn(name = "questionId")
    @JsonIgnore
    private Question question;
    
    @Fetch(FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "survey")
    @JsonIgnore
    private List<Answer> answerList;
}

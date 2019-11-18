package io.jisol.github.sss.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Survey {
    @Id
    @GeneratedValue
    private int qnaId;
    
    @Column
    private String name;
    
    @Column
    private String description;
    
    @OneToOne
    @JoinColumn(name = "questionGroupId")
    private QuestionGroup questionGroup;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "answerId")
    private List<Answer> answerList;
    
    @CreatedDate
    private LocalDateTime createdDate;
}

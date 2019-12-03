package io.jisol.github.sss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Answer extends TimeStampEntity {
    @Id
    @GeneratedValue
    private int answerId;
    
    @ManyToOne
    @JoinColumn(name = "surveyId")
    @JsonIgnore
    private Survey survey;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "answerId")
    private List<AnswerItem> answerItemList;
    
    public static Answer create(Survey survey) {
        Answer answer = new Answer();
        answer.setSurvey(survey);
        return answer;
    }
}

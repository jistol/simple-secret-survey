package io.jisol.github.sss.service;

import io.jisol.github.sss.entity.Answer;
import io.jisol.github.sss.entity.Question;
import io.jisol.github.sss.entity.Survey;
import io.jisol.github.sss.repository.AnswerRespository;
import io.jisol.github.sss.repository.QuestionRespository;
import io.jisol.github.sss.repository.SurveyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    @Autowired
    private SurveyRespository surveyRespository;
    
    @Autowired
    private AnswerRespository answerRespository;
    
    public List<Survey> selectAllSurveyList() {
        return surveyRespository.findAll();
    }

    public Optional<Question> selectQuestionBySurveyId(int surveyId) {
        return surveyRespository.findById(surveyId).map(Survey::getQuestion);
    }
    
    public boolean insertAnswer(int surveyId, Answer answer) {
        Survey survey = surveyRespository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 설문. surveyId : " + surveyId));
        answer.setSurvey(survey);
        answerRespository.save(answer);
        return true;
    }
}

package io.jisol.github.sss.config;

import io.jisol.github.sss.entity.Question;
import io.jisol.github.sss.entity.Survey;
import io.jisol.github.sss.repository.QuestionRespository;
import io.jisol.github.sss.repository.SurveyRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class InitSurveyConfig {
    private final SurveyRespository surveyRespository;
    private final QuestionRespository questionRespository;
    
    @Autowired
    private InitProps initProps;
    
    @PostConstruct
    public void setup() {
        List<Survey> surveyList = initProps.getSurveyList();
        if (surveyList != null && surveyList.size() > 0) {
            List<Question> questionList = surveyList.stream().map(Survey::getQuestion).collect(Collectors.toList());
            questionRespository.saveAll(questionList);
            surveyRespository.saveAll(surveyList);
        }
    }
}

package io.jisol.github.sss.service;

import io.jisol.github.sss.entity.Answer;
import io.jisol.github.sss.entity.AnswerItem;
import io.jisol.github.sss.entity.Question;
import io.jisol.github.sss.entity.Survey;
import io.jisol.github.sss.repository.AnswerRespository;
import io.jisol.github.sss.repository.SurveyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
    
    public List<Answer> getAnswerListBySurveyId(int surveyId) {
        Survey survey = surveyRespository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 설문. surveyId : " + surveyId));
        
        return answerRespository.findBySurvey(survey).orElse(Collections.emptyList());
    }
    
    public Map<String, Object> convertResult(List<Answer> answerList) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("participate", answerList.size());
        
        Map<Integer, IntSummaryStatistics> statMap = answerList.stream()
                .flatMap(answer -> answer.getAnswerItemList().stream())
                .map(item -> new AbstractMap.SimpleEntry<Integer, Integer>(item.getQuestionItemId(), item.getScore()))
                .collect(Collectors.groupingBy(e->e.getKey(), Collectors.summarizingInt(e -> e.getValue())));
    
        double averageByQuestion = statMap.values().stream()
                .mapToDouble(IntSummaryStatistics::getAverage)
                .average()
                .orElse(0);
    
        long totalScore = answerList.stream()
                .flatMap(answer -> answer.getAnswerItemList().stream())
                .mapToInt(AnswerItem::getScore)
                .sum();
    
        resultMap.put("averageByQuestion", Math.round(averageByQuestion));
        resultMap.put("totalScore", totalScore);
        resultMap.put("totalAverage", Math.round(totalScore / answerList.size()));
        return resultMap;
    }
}

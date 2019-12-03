package io.jisol.github.sss.controller;

import io.jisol.github.sss.entity.Answer;
import io.jisol.github.sss.entity.Question;
import io.jisol.github.sss.entity.Survey;
import io.jisol.github.sss.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/surveys")
@RestController
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @GetMapping
    public List<Survey> selectSurveyList() {
        return surveyService.selectAllSurveyList();
    }
    
    @GetMapping("/{surveyId}/question")
    public Question selectQuestionBySurveyId(@PathVariable("surveyId") int surveyId) {
        return surveyService.selectQuestionBySurveyId(surveyId).orElse(null);
    }
    
    @PostMapping("/{surveyId}/answer")
    public Map<String, Object> insertAnswer(@PathVariable("surveyId") int surveyId, @RequestBody Answer answer) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            boolean result = surveyService.insertAnswer(surveyId, answer);
            resultMap.put("result", result);
        } catch (Exception e) {
            resultMap.put("result", false);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
    
    @GetMapping("/{surveyId}/result")
    public Map<String, Object> getSurveyResult(@PathVariable("surveyId") int surveyId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Answer> answerList = surveyService.getAnswerListBySurveyId(surveyId);
            resultMap.put("data", surveyService.convertResult(answerList));
            resultMap.put("result", true);
        } catch (Exception e) {
            resultMap.put("result", false);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
}

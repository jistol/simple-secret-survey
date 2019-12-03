package io.jisol.github.sss.config;

import io.jisol.github.sss.entity.Answer;
import io.jisol.github.sss.entity.AnswerItem;
import io.jisol.github.sss.entity.Question;
import io.jisol.github.sss.entity.QuestionItem;
import io.jisol.github.sss.entity.Survey;
import io.jisol.github.sss.repository.AnswerItemRespository;
import io.jisol.github.sss.repository.AnswerRespository;
import io.jisol.github.sss.repository.QuestionItemRespository;
import io.jisol.github.sss.repository.QuestionRespository;
import io.jisol.github.sss.repository.SurveyRespository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Profile("local")
@Component
@RequiredArgsConstructor
public class LocalOnlyConfig {
    private final QuestionRespository questionRespository;
    private final QuestionItemRespository questionItemRespository;
    private final SurveyRespository surveyRespository;
    private final AnswerRespository answerRespository;
    private final AnswerItemRespository answerItemRespository;
    
    @PostConstruct
    private void setup() {
        Question question = new Question();
        question.setName("Test Question");
        
        List<QuestionItem> questionItemList = createQuestionItem(
                "Question 1",
                "Question 2",
                "Question 3",
                "Last Question"
        );
        question.setQuestionItemList(questionItemList);
        questionRespository.save(question);
        
    
        Survey survey = new Survey();
        survey.setName("Test Survey");
        survey.setDescription("For first test.");
        survey.setQuestion(question);
        
        surveyRespository.save(survey);
    
        List<Answer> answerList = createRandomAnswerList(survey);
        answerList.stream().forEach(answer -> {
            List<AnswerItem> answerItemList = questionItemList.stream()
                    .map(q -> createAnswerItem(q.getQuestionItemId()))
                    .collect(Collectors.toList());
            answer.setAnswerItemList(answerItemList);
        });
    
        answerRespository.saveAll(answerList);
    }
    
    private List<QuestionItem> createQuestionItem(String... descriptions) {
        return Stream.of(descriptions)
                .map(d -> QuestionItem.create(d))
                .collect(Collectors.toList());
    }
    
    private List<Answer> createRandomAnswerList(Survey survey) {
        return IntStream.range(0, RandomUtils.nextInt(5, 10)).boxed()
                .map(i -> Answer.create(survey))
                .collect(Collectors.toList());
    }
    
    private AnswerItem createAnswerItem(int questionItemId) {
        AnswerItem answerItem = new AnswerItem();
        answerItem.setQuestionItemId(questionItemId);
        answerItem.setScore(RandomUtils.nextInt(0, 10));
        return answerItem;
    }
}

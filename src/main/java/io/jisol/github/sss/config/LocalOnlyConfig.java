package io.jisol.github.sss.config;

import io.jisol.github.sss.entity.Answer;
import io.jisol.github.sss.entity.AnswerItem;
import io.jisol.github.sss.entity.Question;
import io.jisol.github.sss.entity.QuestionItem;
import io.jisol.github.sss.entity.Survey;
import io.jisol.github.sss.repository.AnswerRespository;
import io.jisol.github.sss.repository.QuestionRespository;
import io.jisol.github.sss.repository.SurveyRespository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Profile("local")
@Component
@RequiredArgsConstructor
public class LocalOnlyConfig {
    private final QuestionRespository questionRespository;
    private final SurveyRespository surveyRespository;
    private final AnswerRespository answerRespository;
    
    //@PostConstruct
    private void setup() {
        Question question = new Question();
        question.setName("샘플 질문입니다.");
        
        List<QuestionItem> questionItemList = createQuestionItem(
                "샘플 질문1 입니다",
                "샘플 질문2 입니다",
                "샘플 질문3 입니다",
                "샘플 질문4 입니다"
        );
        question.setQuestionItemList(questionItemList);
        questionRespository.save(question);
        
    
        Survey survey = new Survey();
        survey.setName("샘플 설문입니다.");
        survey.setDescription("샘플로 제공하는 설문데이터입니다.");
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

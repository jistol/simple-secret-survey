package io.jisol.github.sss.controller;

import io.jisol.github.sss.repository.SurveyRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DispatchController {
    private final SurveyRespository surveyRespository;
    
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("index")
                .addObject("surveyList", surveyRespository.findAll(Sort.by(Sort.Order.desc("createdDate"))));
    }
}

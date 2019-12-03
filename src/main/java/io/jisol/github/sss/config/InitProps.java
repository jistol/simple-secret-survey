package io.jisol.github.sss.config;

import io.jisol.github.sss.entity.Survey;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="initialize-data")
public class InitProps {
    private List<Survey> surveyList = new ArrayList<>();
}

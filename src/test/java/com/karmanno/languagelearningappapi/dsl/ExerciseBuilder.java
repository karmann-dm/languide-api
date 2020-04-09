package com.karmanno.languagelearningappapi.dsl;

import com.karmanno.languagelearningappapi.entity.Exercise;
import com.karmanno.languagelearningappapi.repository.ExerciseRepository;

public class ExerciseBuilder {
    private final ExerciseRepository exerciseRepository;
    public static final String DEFAULT_TEMPLATE = "{\n" +
            "  \"fields\": [\n" +
            "    {\n" +
            "      \"type\": \"cards\",\n" +
            "      \"options\": {\n" +
            "        \"cardsCount\": 5,\n" +
            "        \"optionsCount\": 4,\n" +
            "        \"perceptionForCard\": 10\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    private String template;
    private String description;
    private Integer version;

    public ExerciseBuilder(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ExerciseBuilder withTemplate(String template) {
        this.template = template;
        return this;
    }

    public ExerciseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ExerciseBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    public Exercise please() {
        return exerciseRepository.save(
                new Exercise()
                    .setVersion(version)
                    .setDescription(description)
                    .setTemplate(template == null ? DEFAULT_TEMPLATE : template)
        );
    }
}

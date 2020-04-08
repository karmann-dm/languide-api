package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

@Data
public class AddExerciseRequest {
    private String description;
    private String template;
}

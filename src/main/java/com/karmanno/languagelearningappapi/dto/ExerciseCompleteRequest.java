package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseCompleteRequest {
    private List<Question> questions;
}

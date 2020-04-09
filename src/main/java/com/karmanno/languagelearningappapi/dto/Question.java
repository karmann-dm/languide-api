package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private Integer literalId;
    private String question;
    private Boolean isAnswered;
    private List<Option> options;
    private Boolean correctAnswer;
}

package com.karmanno.languagelearningappapi.domain;

import lombok.Data;

import java.util.List;

@Data
public class WordCard {
    private String word;
    private List<String> options;
    private Integer correctIndex;
}

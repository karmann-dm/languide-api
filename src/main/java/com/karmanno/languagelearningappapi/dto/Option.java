package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

@Data
public class Option {
    private String title;
    private Boolean correct;
    private Boolean answered;
}

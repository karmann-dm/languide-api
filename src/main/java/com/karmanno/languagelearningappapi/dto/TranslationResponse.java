package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class TranslationResponse {
    private Integer code;
    private String lang;
    private List<String> text;
}

package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

@Data
public class AddLiteralRequest {
    private String sourceLang;
    private String destLang;
    private String word;
    private String translation;
}

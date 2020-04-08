package com.karmanno.languagelearningappapi.domain;

import lombok.Data;

import java.util.Map;

@Data
public class Field {
    private String field;
    private String type;
    private Map<String, Object> options;
}

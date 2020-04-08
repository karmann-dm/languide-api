package com.karmanno.languagelearningappapi.domain;

import lombok.Data;

import java.util.Map;

@Data
public class FilledField {
    ElementType elementType;
    Map<String, Object> values;
}

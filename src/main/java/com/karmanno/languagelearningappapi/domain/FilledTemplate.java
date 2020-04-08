package com.karmanno.languagelearningappapi.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FilledTemplate {
    private List<FilledField> fields = new ArrayList<>();
}


package com.karmanno.languagelearningappapi.service;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.entity.Literal;

import java.util.List;

public interface LiteralService {
    Literal add(Integer userId, Language source, Language dest, String word, String translation);
    List<Literal> getAll(Integer userId, String lang);
}

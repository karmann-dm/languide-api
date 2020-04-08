package com.karmanno.languagelearningappapi.service.processor;

import com.karmanno.languagelearningappapi.domain.ElementType;
import com.karmanno.languagelearningappapi.domain.Field;
import com.karmanno.languagelearningappapi.domain.FilledField;
import com.karmanno.languagelearningappapi.entity.Literal;

import java.util.List;

public interface ElementProcessor {
    boolean acceptableFor(ElementType elementType);
    List<FilledField> process(Field field, List<Literal> literals);
}

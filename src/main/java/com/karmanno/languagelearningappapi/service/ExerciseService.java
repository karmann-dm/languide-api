package com.karmanno.languagelearningappapi.service;

import com.karmanno.languagelearningappapi.dto.ExerciseCompleteRequest;
import com.karmanno.languagelearningappapi.entity.Exercise;
import com.karmanno.languagelearningappapi.entity.Literal;

import java.util.List;

public interface ExerciseService {
    Exercise add(String description, String template);
    Exercise getById(Integer id, Integer userId);
    List<Exercise> getAll();

    List<Literal> complete(Integer id, Integer userId, ExerciseCompleteRequest request);
}

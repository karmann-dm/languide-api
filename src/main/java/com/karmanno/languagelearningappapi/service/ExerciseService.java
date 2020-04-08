package com.karmanno.languagelearningappapi.service;

import com.karmanno.languagelearningappapi.entity.Exercise;

import java.util.List;

public interface ExerciseService {
    Exercise add(String description, String template);
    Exercise edit(Exercise entity);
    Exercise getById(Integer id, Integer userId);
    List<Exercise> getAll();
    void remove(Exercise exercise);

    void complete(Integer id);
}

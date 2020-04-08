package com.karmanno.languagelearningappapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmanno.languagelearningappapi.entity.Exercise;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.repository.ExerciseRepository;
import com.karmanno.languagelearningappapi.repository.UserRepository;
import com.karmanno.languagelearningappapi.service.processor.TemplateProcessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ObjectMapper objectMapper;
    private final TemplateProcessor templateProcessor;
    private final UserRepository userRepository;

    @Override
    public Exercise add(String description, String template) {
        Exercise exercise = new Exercise()
                .setTemplate(template)
                .setDescription(description)
                .setVersion(1);
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise edit(Exercise entity) {
        return exerciseRepository.save(entity);
    }

    @Override
    @SneakyThrows
    public Exercise getById(Integer id, Integer userId) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No exercise with id = " + id));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No user with id = " + userId));
        exercise.setTemplate(templateProcessor.process(
                exercise.getTemplate(), user
        ));
        return exercise;
    }

    @Override
    public List<Exercise> getAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public void remove(Exercise exercise) {
        exerciseRepository.delete(exercise);
    }

    @Override
    public void complete(Integer id) {

    }
}

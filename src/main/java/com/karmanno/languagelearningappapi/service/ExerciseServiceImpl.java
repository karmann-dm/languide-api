package com.karmanno.languagelearningappapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmanno.languagelearningappapi.domain.Field;
import com.karmanno.languagelearningappapi.domain.Template;
import com.karmanno.languagelearningappapi.dto.ExerciseCompleteRequest;
import com.karmanno.languagelearningappapi.entity.Exercise;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.repository.ExerciseRepository;
import com.karmanno.languagelearningappapi.repository.LiteralRepository;
import com.karmanno.languagelearningappapi.repository.UserRepository;
import com.karmanno.languagelearningappapi.service.processor.TemplateProcessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ObjectMapper objectMapper;
    private final TemplateProcessor templateProcessor;
    private final UserRepository userRepository;
    private final LiteralRepository literalRepository;

    @Override
    public Exercise add(String description, String template) {
        Exercise exercise = new Exercise()
                .setTemplate(template)
                .setDescription(description)
                .setVersion(1);
        return exerciseRepository.save(exercise);
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
    @SneakyThrows
    @Transactional
    public List<Literal> complete(Integer id, Integer userId, ExerciseCompleteRequest request) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No exercise with id = " + id));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No user with id = " + userId));
        Template template = objectMapper.readValue(exercise.getTemplate(), Template.class);
        Field field = template.getFields()
                .stream()
                .filter(f -> f.getType().equalsIgnoreCase("cards"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No cards in template"));

        Integer perception = (Integer) field.getOptions().get("perceptionForCard");
        return request.getQuestions()
                .stream()
                .map(question -> {
                    Literal literal = literalRepository.findById(question.getLiteralId())
                            .orElseThrow(() -> new RuntimeException("No literal with id = " + question.getLiteralId()));
                    if (question.getCorrectAnswer()) {
                        literal.setPerception(
                                Math.min(literal.getPerception() + perception, 100)
                        );
                        return literalRepository.save(literal);
                    }
                    return literal;
                })
                .collect(Collectors.toList());
    }
}

package com.karmanno.languagelearningappapi.controller.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karmanno.languagelearningappapi.controller.IntegrationTest;
import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dsl.Given;
import com.karmanno.languagelearningappapi.dto.AuthRequest;
import com.karmanno.languagelearningappapi.dto.ExerciseCompleteRequest;
import com.karmanno.languagelearningappapi.dto.Option;
import com.karmanno.languagelearningappapi.dto.Question;
import com.karmanno.languagelearningappapi.entity.Exercise;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.repository.ExerciseRepository;
import com.karmanno.languagelearningappapi.repository.LiteralRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CompleteExerciseTest extends IntegrationTest {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private LiteralRepository literalRepository;

    @Test
    public void positive() throws Exception {
        User user = Given.user(authService)
                .withUsername("user")
                .withPassword("pass")
                .withLanguage(Language.DE)
                .please();
        Exercise exercise = Given.exercise(exerciseRepository)
                .withDescription("description")
                .withVersion(1)
                .please();
        List<Literal> literals = Given.literal(literalRepository)
                .withLiteral(user, Language.EN, Language.DE, "w1", "t1", 0)
                .withLiteral(user, Language.EN, Language.DE, "w2", "t2", 0)
                .withLiteral(user, Language.EN, Language.DE, "w3", "t3",30)
                .withLiteral(user, Language.EN, Language.DE, "w4", "t4", 0)
                .withLiteral(user, Language.EN, Language.DE, "w5", "t5", 100)
                .please();

        List<Question> questions = new ArrayList<Question>() {{
            add(new Question()
                    .setLiteralId(literals.get(0).getId())
                    .setIsAnswered(true)
                    .setOptions(new ArrayList<Option>() {{
                        add(new Option().setTitle("o1").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("o2").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("t1").setAnswered(true).setCorrect(true));
                        add(new Option().setTitle("o4").setAnswered(false).setCorrect(false));
                    }})
                    .setCorrectAnswer(true)
                    .setQuestion(literals.get(0).getWord()));
            add(new Question()
                    .setLiteralId(literals.get(1).getId())
                    .setIsAnswered(true)
                    .setOptions(new ArrayList<Option>() {{
                        add(new Option().setTitle("o1").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("o2").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("t2").setAnswered(true).setCorrect(true));
                        add(new Option().setTitle("o4").setAnswered(false).setCorrect(false));
                    }})
                    .setCorrectAnswer(true)
                    .setQuestion(literals.get(1).getWord()));
            add(new Question()
                    .setLiteralId(literals.get(2).getId())
                    .setIsAnswered(true)
                    .setOptions(new ArrayList<Option>() {{
                        add(new Option().setTitle("o1").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("o2").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("t3").setAnswered(true).setCorrect(true));
                        add(new Option().setTitle("o4").setAnswered(false).setCorrect(false));
                    }})
                    .setCorrectAnswer(true)
                    .setQuestion(literals.get(2).getWord()));
            add(new Question()
                    .setLiteralId(literals.get(3).getId())
                    .setIsAnswered(true)
                    .setOptions(new ArrayList<Option>() {{
                        add(new Option().setTitle("o1").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("t4").setAnswered(false).setCorrect(true));
                        add(new Option().setTitle("o3").setAnswered(true).setCorrect(false));
                        add(new Option().setTitle("o4").setAnswered(false).setCorrect(false));
                    }})
                    .setCorrectAnswer(false)
                    .setQuestion(literals.get(3).getWord()));
            add(new Question()
                    .setLiteralId(literals.get(4).getId())
                    .setIsAnswered(true)
                    .setOptions(new ArrayList<Option>() {{
                        add(new Option().setTitle("o1").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("o2").setAnswered(false).setCorrect(false));
                        add(new Option().setTitle("t5").setAnswered(true).setCorrect(true));
                        add(new Option().setTitle("o4").setAnswered(false).setCorrect(false));
                    }})
                    .setCorrectAnswer(true)
                    .setQuestion(literals.get(4).getWord()));
        }};

        performAuthorizedJsonPostWithBody(
                "/exercise/" + exercise.getId(),
                new ExerciseCompleteRequest()
                        .setQuestions(
                                questions
                        ),
                new AuthRequest()
                    .setUsername("user")
                    .setPassword("pass")
                ).andExpect(
                        jsonPath("$.success").value(true)
        )
        .andExpect(
                jsonPath("$.payload[0].id").value(literals.get(0).getId())
        )
                .andExpect(
                        jsonPath("$.payload[0].perception").value(10)
                )
                .andExpect(
                        jsonPath("$.payload[1].perception").value(10)
                )
                .andExpect(
                        jsonPath("$.payload[2].perception").value(40)
                )
                .andExpect(
                        jsonPath("$.payload[3].perception").value(0)
                )
                .andExpect(
                        jsonPath("$.payload[4].perception").value(100)
                );
    }
}
